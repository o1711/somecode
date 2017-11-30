/**
 * 
 */
package com.graphscape.commons.http.provider.netty;

import static org.jboss.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.http.HttpRequestHandlerI;
import com.graphscape.commons.http.HttpResponseI;
import com.graphscape.commons.http.spi.HttpServiceProviderI;

/**
 * @author wuzhen
 * 
 */
public class HttpUpstreamHandler extends SimpleChannelUpstreamHandler {

	private static final Logger LOG = LoggerFactory
			.getLogger(HttpUpstreamHandler.class);

	private HttpServiceProviderI spi;

	public HttpUpstreamHandler(HttpServiceProviderI spi) {
		this.spi = spi;

	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		if (LOG.isTraceEnabled()) {

			LOG.trace("messageReceived");

		}
		HttpRequest request = (HttpRequest) e.getMessage();

		NettyHttpRequest nreq = new NettyHttpRequest(request);
		HttpRequestContext reqCtx = new HttpRequestContext(ctx, nreq);
		ctx.setAttachment(reqCtx);
		//
		HttpRequestHandlerI h = this.spi.resolveHttpRequestHandler(nreq);
		if (LOG.isDebugEnabled()) {
			LOG.debug("handler resovled:" + h);
		}
		h.messageReceived(reqCtx);

		if (LOG.isTraceEnabled()) {
			LOG.trace("end of messageReceived");
		}

		reqCtx.endOfResponse();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		HttpRequestContext reqCtx = (HttpRequestContext) ctx.getAttachment();
		Channel ch = e.getChannel();
		Throwable cause = e.getCause();
		LOG.error("", cause);

		HttpResponseI response = reqCtx.getResponse();
		if (response != null) {//
			response.write("Internal Server Error,Cause:" + e.getCause());
			reqCtx.endOfResponse(true);
			return;
		}

		if (cause instanceof TooLongFrameException) {
			reqCtx.sendError(BAD_REQUEST);
			return;
		}

		if (ch.isConnected()) {
			reqCtx.sendError(INTERNAL_SERVER_ERROR);
		}
	}
}
