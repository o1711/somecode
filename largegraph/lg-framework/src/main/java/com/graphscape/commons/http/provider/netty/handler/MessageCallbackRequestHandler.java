/**
 * 
 */
package com.graphscape.commons.http.provider.netty.handler;

import java.io.InputStreamReader;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.graphscape.commons.http.HttpRequestContextI;
import com.graphscape.commons.http.HttpRequestI;
import com.graphscape.commons.http.provider.netty.HttpRequestContext;
import com.graphscape.commons.http.provider.netty.NettyHttpRequest;
import com.graphscape.commons.http.provider.netty.NettyHttpResponse;
import com.graphscape.commons.http.support.HttpRequestHandlerSupport;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.SimpleJsonMarshallingProvider;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.provider.default_.DefaultMessage;

/**
 * @author wuzhen
 * 
 */
public class MessageCallbackRequestHandler extends HttpRequestHandlerSupport {

	private static final Logger LOG = LoggerFactory
			.getLogger(MessageCallbackRequestHandler.class);

	private MarshallingProviderI codecFactory;

	private MarshallerI messageCodec;

	private CallbackI<MessageI, MessageI> callback;

	private JsonParser parser = new JsonParser();
	private Gson gson = new GsonBuilder().create();
	public MessageCallbackRequestHandler(CallbackI<MessageI, MessageI> callback) {
		this.callback = callback;
		this.codecFactory = new SimpleJsonMarshallingProvider();
		this.messageCodec = this.codecFactory.getMarshaller(MessageI.class);
	}

	@Override
	public void messageReceivedInternal(HttpRequestI req,
			HttpRequestContextI context) throws Exception {
		HttpRequest request = ((NettyHttpRequest) req).getTarget();
		HttpRequestContext ctx = (HttpRequestContext) context;

		ChannelBufferInputStream is = new ChannelBufferInputStream(
				request.getContent());
		JsonArray jA1 = (JsonArray) this.parser.parse(new InputStreamReader(is));
		MessageI inMsg = null;
		try {
			inMsg = (MessageI) this.messageCodec.unmarshal(jA1);
		} catch (RuntimeException e) {
			LOG.error("exception got when decode:" + jA1);
			throw e;
		}

		MessageI outMsg = this.onMessage(inMsg);

		JsonArray jA2 = null;
		try {
			jA2 = (JsonArray) this.messageCodec.marshal(outMsg);
		} catch (RuntimeException e) {
			LOG.error("exception got when encode:" + outMsg);
			throw e;
		}
		String content = gson.toJson(jA2);

		HttpResponseStatus status = HttpResponseStatus.OK;

		NettyHttpResponse resp = ctx.response(status.getCode());

		byte[] bts = content.getBytes();
		ChannelBuffer buf = ChannelBuffers.copiedBuffer(bts);
		resp.setContent(buf);

		resp.setHeader(HttpHeaders.Names.CONTENT_TYPE, "application/json");

		resp.setHeader(HttpHeaders.Names.CONTENT_LENGTH,
				String.valueOf(buf.readableBytes()));

		ChannelFuture future = ctx.getChannel().write(resp);

		future.awaitUninterruptibly();// TODO timeout

		future.addListener(ChannelFutureListener.CLOSE);

	}

	/**
	 * @param inMsg
	 * @return
	 */
	private MessageI onMessage(MessageI inMsg) {
		if (this.callback == null) {
			MessageI rt = new DefaultMessage(inMsg);
			rt.getErrorInfos().add("no callback for message processing");
			return rt;
		} else {
			return this.callback.execute(inMsg);//
		}

	}

}