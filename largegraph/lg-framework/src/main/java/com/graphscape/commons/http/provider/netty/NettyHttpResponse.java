/**
 * 
 */
package com.graphscape.commons.http.provider.netty;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CACHE_CONTROL;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.DATE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.EXPIRES;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.LAST_MODIFIED;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.activation.MimetypesFileTypeMap;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders.Names;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.graphscape.commons.http.HttpResponseI;
import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen
 * 
 */
public class NettyHttpResponse extends NettyHttpMessage<HttpResponse> implements
		HttpResponseI {

	public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
	public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
	public static final int HTTP_CACHE_SECONDS = 60;

	private Channel channel;

	private boolean headerWriten;

	private HttpRequestContext context;

	/**
	 * @return the response
	 */
	public HttpResponse getResponse() {
		return this.target;
	}

	/**
	 * @param http11
	 * @param notModified
	 */
	public NettyHttpResponse(HttpRequestContext context) {
		super(null);
		this.context = context;
		this.channel = context.getChannel();
	}

	public void response(HttpVersion http11, HttpResponseStatus notModified) {
		this.target = new DefaultHttpResponse(http11, notModified);

	}

	/**
	 * Sets the Date header for the HTTP response
	 * 
	 * @param response
	 *            HTTP response
	 */
	public void setDateHeader() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT,
				Locale.US);
		dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

		Calendar time = new GregorianCalendar();
		target.setHeader(DATE, dateFormatter.format(time.getTime()));
	}

	/**
	 * Sets the Date and Cache headers for the HTTP Response
	 * 
	 * @param response
	 *            HTTP response
	 * @param fileToCache
	 *            file to extract content type
	 */
	public void setDateAndCacheHeaders(long lastModified) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT,
				Locale.US);
		dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));

		// Date header
		Calendar time = new GregorianCalendar();
		target.setHeader(DATE, dateFormatter.format(time.getTime()));

		// Add cache headers
		time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
		target.setHeader(EXPIRES, dateFormatter.format(time.getTime()));
		target.setHeader(CACHE_CONTROL, "private, max-age="
				+ HTTP_CACHE_SECONDS);
		target.setHeader(LAST_MODIFIED,
				dateFormatter.format(new Date(lastModified)));
	}

	/**
	 * Sets the content type header for the HTTP Response
	 * 
	 * @param response
	 *            HTTP response
	 * @param file
	 *            file to extract content type
	 */
	public void setContentTypeHeader(String path) {
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		target.setHeader(CONTENT_TYPE, mimeTypesMap.getContentType(path));
	}

	@Override
	public void setHeader(String name, String value) {
		this.target.setHeader(name, value);
	}

	/**
	 * @param copiedBuffer
	 */
	public void setContent(ChannelBuffer content) {
		this.target.setContent(content);
	}

	/**
	 * @param fileLength
	 */
	public void setContentLength(long length) {
		this.target.setHeader(Names.CONTENT_LENGTH, length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.http.HttpMessageI#getHeader(java.lang.String)
	 */
	@Override
	public String getHeader(String key) {

		return this.target.getHeader(key);
	}

	public void assertHeaderNotWriten() {
		if (this.headerWriten) {
			throw new GsException("header already writen.");
		}
	}

	public void assertHeaderWriten() {
		if (this.headerWriten) {
			return;
		}
		this.write();
		this.headerWriten = true;
	}

	@Override
	public void write() {
		this.assertHeaderNotWriten();
		context.write(this.target);
	}

	public void write(Object msg) {
		this.assertHeaderWriten();
		context.write(msg);
	}

	@Override
	public void write(String content) {

		ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
		ChannelBuffer cbuffer = ChannelBuffers.copiedBuffer(content,
				StandardCharsets.UTF_8);
		this.write(cbuffer);
	}

}
