/**
 * 
 */
package com.graphscape.commons.http.provider.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import com.graphscape.commons.http.spi.HttpServiceProviderI;

/**
 * @author wuzhen
 * 
 */
public class NettyHttpChannelPipelineFactory implements ChannelPipelineFactory {

	protected HttpServiceProviderI spi;
	
	protected HttpUpstreamHandler upstreamHandler;

	public NettyHttpChannelPipelineFactory(HttpServiceProviderI spi) {
		this.spi = spi;
		this.upstreamHandler = new HttpUpstreamHandler(this.spi);
		
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("encoder", new HttpResponseEncoder());
		pipeline.addLast("handler", this.upstreamHandler);
		
		return pipeline;
	}

}
