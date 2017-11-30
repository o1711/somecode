/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.graphscape.commons.http.provider.netty;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class OpenChannelsHandler implements ChannelUpstreamHandler {

	final Set<Channel> openChannels = Collections
			.synchronizedSet(new HashSet<Channel>());
	final AtomicLong openChannelsMetric = new AtomicLong();
	final AtomicLong totalChannelsMetric = new AtomicLong();

	private static final Logger LOG = LoggerFactory
			.getLogger(OpenChannelsHandler.class);

	public OpenChannelsHandler() {

	}

	final ChannelFutureListener remover = new ChannelFutureListener() {
		public void operationComplete(ChannelFuture future) throws Exception {
			boolean removed = openChannels.remove(future.getChannel());
			if (removed) {
				openChannelsMetric.decrementAndGet();
			}

			if (LOG.isTraceEnabled()) {
				LOG.trace("channel closed: {}", future.getChannel());
			}
		}
	};

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		if (e instanceof ChannelStateEvent) {
			ChannelStateEvent evt = (ChannelStateEvent) e;
			// OPEN is also sent to when closing channel, but with FALSE on it
			// to indicate it closes
			if (evt.getState() == ChannelState.OPEN
					&& Boolean.TRUE.equals(evt.getValue())) {
				if (LOG.isTraceEnabled()) {
					LOG.trace("channel opened: {}", ctx.getChannel());
				}
				boolean added = openChannels.add(ctx.getChannel());
				if (added) {
					openChannelsMetric.incrementAndGet();
					totalChannelsMetric.incrementAndGet();
					ctx.getChannel().getCloseFuture().addListener(remover);
				}
			}
		}
		ctx.sendUpstream(e);
	}

	public long numberOfOpenChannels() {
		return openChannelsMetric.get();
	}

	public long totalChannels() {
		return totalChannelsMetric.get();
	}

	public void close() {
		for (Channel channel : openChannels) {
			channel.close().awaitUninterruptibly();
		}
	}
}
