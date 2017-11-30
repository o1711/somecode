/**
 * 
 */
package com.graphscape.commons.http.provider.netty;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.NOT_MODIFIED;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;

import com.graphscape.commons.http.HttpRequestContextI;
import com.graphscape.commons.http.HttpRequestI;
import com.graphscape.commons.http.HttpResponseI;
import com.graphscape.commons.http.ResponseStatus;
import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen
 * 
 */
public class HttpRequestContext implements HttpRequestContextI {

	private ChannelHandlerContext channelHandlerContext;

	private NettyHttpResponse response;

	private NettyHttpRequest request;

	private ChannelFuture lastChannelFuture;

	private boolean end;

	private boolean close;

	/**
	 * @return the lastChannelFuture
	 */
	public ChannelFuture getLastChannelFuture() {
		return lastChannelFuture;
	}

	public HttpRequestContext(ChannelHandlerContext chc,
			NettyHttpRequest request) {
		this.request = request;
		this.channelHandlerContext = chc;
	}

	public void sendError(HttpResponseStatus status) {
		response(status);
		response.setHeader(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.setContent(ChannelBuffers.copiedBuffer(
				"Failure: " + status.toString() + "\r\n", CharsetUtil.UTF_8));

		// Close the connection as soon as the error message is sent.
		this.response.write();
		this.endOfResponse(true);
	}

	@Override
	public NettyHttpResponse response() {
		return this.response(ResponseStatus.OK);
	}

	@Override
	public NettyHttpResponse response(int status) {
		return this.response(HttpResponseStatus.valueOf(status));
	}

	private NettyHttpResponse response(HttpResponseStatus status) {
		return response(HTTP_1_1, status);
	}

	private NettyHttpResponse response(HttpVersion version,
			HttpResponseStatus status) {
		if (this.response != null) {
			throw new GsException("already started response");
		}
		this.response = new NettyHttpResponse(this);
		this.response.response(version, status);

		return this.response;
	}

	public void sendNotModified() {
		this.response(NOT_MODIFIED);
		this.response.setDateHeader();
		this.response.write();
		this.endOfResponse(true);
	}

	@Override
	public HttpRequestI getRequest() {
		return this.request;
	}

	public String getSanitizeUri() {
		String uri = this.request.getUri();
		// Decode the path.
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			try {
				uri = URLDecoder.decode(uri, "ISO-8859-1");
			} catch (UnsupportedEncodingException e1) {
				throw new Error();
			}
		}

		return uri;
	}

	public ChannelHandlerContext getChannelHandlerContext() {
		return this.channelHandlerContext;
	}

	/**
	 * @return
	 */
	public Channel getChannel() {
		return this.channelHandlerContext.getChannel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.http.HttpRequestContextI#getResponse()
	 */
	@Override
	public HttpResponseI getResponse() {
		// TODO Auto-generated method stub
		return this.response;
	}

	/**
	 * @param obj
	 */
	public void write(Object obj) {
		this.lastChannelFuture = this.channelHandlerContext.getChannel().write(
				obj);
	}

	public void endOfResponse() {
		boolean close = !HttpHeaders.isKeepAlive(request.getTarget());
		this.endOfResponse(close);
	}

	@Override
	public void endOfResponse(boolean close) {
		if (this.end) {
			throw new GsException("already end.");
		}
		this.end = true;
		this.close = close;
		if (close) {
			this.getLastChannelFuture()
					.addListener(ChannelFutureListener.CLOSE);
		}
	}

}
