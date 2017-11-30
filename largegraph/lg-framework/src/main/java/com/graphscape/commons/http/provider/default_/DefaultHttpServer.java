/**
 * 
 */
package com.graphscape.commons.http.provider.default_;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.graphscape.commons.configuration.ConfigurableI;
import com.graphscape.commons.container.ContainerAwareI;
import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.http.HttpServerI;
import com.graphscape.commons.http.provider.netty.NettyHttpChannelPipelineFactory;
import com.graphscape.commons.http.spi.HttpServiceProviderI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.support.ConfigurableLifeCycleSupport;

/**
 * @author wuzhen
 * 
 */
public class DefaultHttpServer extends ConfigurableLifeCycleSupport implements
		HttpServerI, ConfigurableI, ContainerAwareI {

	private ServerBootstrap serverBootstrap;

	private Channel serverChannel;

	private NettyHttpChannelPipelineFactory pipelineFactory;

	protected HttpServiceProviderI spi;

	ContainerI container;

	public DefaultHttpServer() {

	}

	public DefaultHttpServer(HttpServiceProviderI spi) {
		this.spi = spi;
	}

	@Override
	public void start() {

		try {
			this.doStart();
		} catch (Exception e) {
			throw GsException.toRuntimeException(e);
		}

	}

	@Override
	public void doStart(){
		this.pipelineFactory = new NettyHttpChannelPipelineFactory(this.spi);
		ChannelFactory fact = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(new EsThreadFactory("[Master]")),
				Executors.newCachedThreadPool(new EsThreadFactory("[Slaver]")));
		serverBootstrap = new ServerBootstrap(fact);

		serverBootstrap.setPipelineFactory(this.pipelineFactory);

		int port = this.config.getPropertyAsInt("port", -1);
		if (port == -1) {
			throw new GsException("please set port for http server in config:"
					+ this.config.getId());
		}
		serverChannel = serverBootstrap.bind(new InetSocketAddress(port));

	}

	@Override
	public void doShutdown() {
		if (serverChannel != null) {
			serverChannel.close().awaitUninterruptibly();
			serverChannel = null;
		}

		if (serverBootstrap != null) {
			serverBootstrap.releaseExternalResources();
			serverBootstrap = null;
		}
	}

	private static class EsThreadFactory implements ThreadFactory {
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

		public EsThreadFactory(String namePrefix) {
			this.namePrefix = namePrefix;
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread()
					.getThreadGroup();
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r, namePrefix + "[T#"
					+ threadNumber.getAndIncrement() + "]", 0);
			t.setDaemon(true);
			return t;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.container.ContainerAwareI#setContainer(com.graphscape
	 * .commons.container.ContainerI)
	 */
	@Override
	public void setContainer(ContainerI c) {
		this.container = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.container.ContainerAwareI#getContainer()
	 */
	@Override
	public ContainerI getContainer() {

		return this.container;
	}

}
