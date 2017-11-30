package com.graphscape.commons.http.provider.netty.handler;

import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.IF_MODIFIED_SINCE;
import static org.jboss.netty.handler.codec.http.HttpMethod.GET;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.RandomAccessContent;
import org.apache.commons.vfs2.util.RandomAccessMode;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelFutureProgressListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.FileRegion;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.handler.stream.ChunkedStream;

import com.graphscape.commons.http.HttpRequestContextI;
import com.graphscape.commons.http.HttpRequestI;
import com.graphscape.commons.http.provider.netty.HttpRequestContext;
import com.graphscape.commons.http.provider.netty.NettyHttpRequest;
import com.graphscape.commons.http.provider.netty.NettyHttpResponse;
import com.graphscape.commons.http.provider.netty.RandomAccessContentRegion;
import com.graphscape.commons.http.support.HttpRequestHandlerSupport;

/**
 * A simple handler that serves incoming HTTP requests to send their respective
 * HTTP responses. It also implements {@code 'If-Modified-Since'} header to take
 * advantage of browser cache, as described in <a
 * href="http://tools.ietf.org/html/rfc2616#section-14.25">RFC 2616</a>.
 * 
 * <h3>How Browser Caching Works</h3>
 * 
 * Web browser caching works with HTTP headers as illustrated by the following
 * sample:
 * <ol>
 * <li>Request #1 returns the content of {@code /file1.txt}.</li>
 * <li>Contents of {@code /file1.txt} is cached by the browser.</li>
 * <li>Request #2 for {@code /file1.txt} does return the contents of the file
 * again. Rather, a 304 Not Modified is returned. This tells the browser to use
 * the contents stored in its cache.</li>
 * <li>The server knows the file has not been modified because the
 * {@code If-Modified-Since} date is the same as the file's last modified date.</li>
 * </ol>
 * 
 * <pre>
 * Request #1 Headers
 * ===================
 * GET /file1.txt HTTP/1.1
 * 
 * Response #1 Headers
 * ===================
 * HTTP/1.1 200 OK
 * Date: Tue, 01 Mar 2011 22:44:26 GMT
 * Last-Modified: Wed, 30 Jun 2010 21:36:48 GMT
 * Expires: Tue, 01 Mar 2012 22:44:26 GMT
 * Cache-Control: private, max-age=31536000
 * 
 * Request #2 Headers
 * ===================
 * GET /file1.txt HTTP/1.1
 * If-Modified-Since: Wed, 30 Jun 2010 21:36:48 GMT
 * 
 * Response #2 Headers
 * ===================
 * HTTP/1.1 304 Not Modified
 * Date: Tue, 01 Mar 2011 22:44:28 GMT
 * 
 * </pre>
 */
public class FileRequestHandler extends HttpRequestHandlerSupport {

	

	private FileSystemManager fileSystemManager;

	/**
	 * @param fileSystemManager
	 */
	public FileRequestHandler(FileSystemManager fsm) {
		this.fileSystemManager = fsm;
	}

	@Override
	public void messageReceivedInternal(HttpRequestI req,
			HttpRequestContextI context) throws Exception {
		HttpRequest request = ((NettyHttpRequest)req).getTarget();
		HttpRequestContext ctx = (HttpRequestContext)context;
		
		if (request.getMethod() != GET) {
			ctx.sendError(METHOD_NOT_ALLOWED);
			return;
		}

		final String path = sanitizeUri(request.getUri());
		if (path == null) {
			ctx.sendError(FORBIDDEN);
			return;
		}
		// open file
		FileObject fileObj = this.fileSystemManager.resolveFile(path);

		if (fileObj.isHidden() || !fileObj.exists()) {
			ctx.sendError(NOT_FOUND);
			return;
		}
		// not a file
		if (!FileType.FILE.equals(fileObj.getType())) {
			ctx.sendError(FORBIDDEN);
			return;
		}

		// Cache Validation
		String ifModifiedSince = request.getHeader(IF_MODIFIED_SINCE);
		if (ifModifiedSince != null && ifModifiedSince.length() != 0) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(
					NettyHttpResponse.HTTP_DATE_FORMAT, Locale.US);
			Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);

			// Only compare up to the second because the datetime format we send
			// to the client does
			// not have milliseconds
			long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
			long fileLastModifiedSeconds = fileObj.getContent()
					.getLastModifiedTime() / 1000;
			if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
				ctx.sendNotModified();
				return;
			}
		}

		FileContent fc = fileObj.getContent();
		RandomAccessContent raf = fc
				.getRandomAccessContent(RandomAccessMode.READ);

		//
		long fileLength = raf.length();
		NettyHttpResponse response = ctx.response(OK.getCode());
		response.setContentLength(fileLength);
		response.setContentTypeHeader(path);
		response.setDateAndCacheHeaders(fileObj.getContent()
				.getLastModifiedTime());


		// Write the initial line and the header.
		response.write();//
		// Write the content.
		ChannelFuture writeFuture;
		Channel ch = ctx.getChannel();
		if (ctx.getChannel().getPipeline().get(SslHandler.class) != null) {
			// Cannot use zero-copy with HTTPS.
			response.write(new ChunkedStream(raf.getInputStream()));
		} else {
			// No encryption - use zero-copy.
			final FileRegion region = new RandomAccessContentRegion(raf, 0, fileLength);
			writeFuture = ch.write(region);
			writeFuture.addListener(new ChannelFutureProgressListener() {
				public void operationComplete(ChannelFuture future) {
					region.releaseExternalResources();
				}

				public void operationProgressed(ChannelFuture future,
						long amount, long current, long total) {
					// ("%s: %d / %d (+%d)%n", path, current,
					// total, amount);
				}
			});
		}

		
	}

	private static String sanitizeUri(String uri) {

		// Convert file separators.
		uri = uri.replace('/', File.separatorChar);

		// Simplistic dumb security check.
		// You will have to do something serious in the production environment.
		if (uri.contains(File.separator + '.')
				|| uri.contains('.' + File.separator) || uri.startsWith(".")
				|| uri.endsWith(".")) {
			return null;
		}

		// Convert to absolute path.
		return System.getProperty("user.dir") + File.separator + uri;
	}

}