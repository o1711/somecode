/**
 * 
 */
package com.graphscape.commons.handling.support;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.cache.CacheElement;
import com.graphscape.commons.cache.CacheI;
import com.graphscape.commons.cache.provider.DefaultCache;
import com.graphscape.commons.handling.HandlingContextI;
import com.graphscape.commons.lang.AdaptableI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.HasPathI;
import com.graphscape.commons.util.Node;
import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.Tree;
import com.graphscape.commons.util.TreeImpl;

/**
 * @author wuzhen
 * 
 */
public class PathBasedHandlerResolver<S, X, T extends HandlingContextI<S, X>> extends
		HandlerResolverSupport<S, X, T> {
	
	/**
	 * Each node has one NodeHandlers.
	 * 
	 * @author wuzhen0808@gmail.com
	 * 
	 * @param <S>
	 * @param <X>
	 * @param <T>
	 */
	public static class NodeEntry<T> {

		protected List<HandlerEntry<T>> handlers = new ArrayList<HandlerEntry<T>>();

		public NodeEntry() {
		}

		public void addHandler(Path p, boolean strict, HandlerI<T> h) {

			this.handlers.add(new HandlerEntry<T>(p, strict, h));

		}

		/**
		 * 
		 * @param p
		 * @param t
		 * @param touched
		 *            the actural handler
		 * @return how many handler is called for this message.
		 */
		public List<HandlerI<T>> getMatchedHandler(Path p) {
			List<HandlerI<T>> rt = new ArrayList<HandlerI<T>>();
			for (HandlerEntry<T> h : handlers) {
				boolean hs = h.tryHandle(p);

				if (hs) {
					rt.add(h.target);
				}

			}
			return rt;
		}

	}

	/**
	 * 
	 * @author wuzhen0808@gmail.com
	 * 
	 * @param <S>
	 * @param <X>
	 * @param <T>
	 */
	public static class HandlerEntry<T> {

		private Path path;

		private boolean strict;// strictly match the path,the path configurated
								// is equals to the path of message.

		private HandlerI<T> target;

		public HandlerEntry(Path p, boolean strict, HandlerI<T> h) {
			this.path = p;
			this.strict = strict;
			this.target = h;
		}

		/**
		 * 
		 * @param p
		 * @param sc
		 * @return whether the message is really handled by the handler.
		 */
		public boolean tryHandle(Path p) {
			if (this.strict && p.equals(this.path) || !this.strict && path.isSubPath(p, true)) {
				// path matched
				// do filter
				return true;
			}
			return false;
		}

	}

	protected static final Logger LOG = LoggerFactory.getLogger(PathBasedHandlerResolver.class);
	protected CacheI<HandlerI<T>> handlers_cache = new DefaultCache<HandlerI<T>>();

	private Tree<NodeEntry<T>> tree;

	// protected MessageServiceProviderI<T> mspi;

	public PathBasedHandlerResolver() {
		this.tree = new TreeImpl<NodeEntry<T>>();
	}

	public static Path getPath(Object mc) {
		if (mc instanceof HasPathI) {
			return ((HasPathI) mc).getPath();
		}
		if (mc instanceof AdaptableI) {
			return ((AdaptableI) mc).getInterface(HasPathI.class, true).getPath();
		}
		throw new GsException("not support HasPathI");
	}

	public void addHandler(Path p, HandlerI<T> h) {
		this.addHandler(p, true, h);
	}

	public void addHandler(Path p, boolean strict, HandlerI<T> h) {
		this.handlers_cache.clear();
		Node<NodeEntry<T>> node = this.tree.getOrCreateNode(p);
		NodeEntry<T> cl = node.getTarget();
		if (cl == null) {
			cl = new NodeEntry<T>();
			node.setTarget(cl);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("add handler,path:" + p + ",strict:" + strict + ",handler:" + h);

		}
		cl.addHandler(p, strict, h);

	}

	@Override
	public HandlerI<T> doResolve(final T mc) {

		Path path = this.getPath(mc);
		CacheElement<HandlerI<T>> rtE = handlers_cache.getElement(path);
		if (rtE == null) {
			HandlerI<T> hdl = this.doResolve(path);
			rtE = handlers_cache.put(path, hdl);
		}

		return rtE.getTarget();
	}

	protected HandlerI<T> doResolve(Path path) {
		List<NodeEntry<T>> chL = this.tree.getTargetListInPath(path);
		List<HandlerI<T>> hL = new ArrayList<HandlerI<T>>();

		for (NodeEntry<T> nh : chL) {
			if (nh == null) {
				continue;
			}
			List<HandlerI<T>> mL = nh.getMatchedHandler(path);
			hL.addAll(mL);
		}
		if (hL.isEmpty()) {
			return null;
		}
		CollectionHandler<S, X, T> rt = new CollectionHandler<S, X, T>(hL);
		return rt;
	}

}
