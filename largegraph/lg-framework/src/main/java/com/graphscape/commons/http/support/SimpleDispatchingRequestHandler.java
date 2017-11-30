/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http.support;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.http.HttpRequestContextI;
import com.graphscape.commons.http.HttpRequestHandlerI;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class SimpleDispatchingRequestHandler implements HttpRequestHandlerI {
	private static final Logger LOG = LoggerFactory
			.getLogger(SimpleDispatchingRequestHandler.class);
	private Map<Path, HttpRequestHandlerI> handlerMap = new HashMap<Path, HttpRequestHandlerI>();

	private HttpRequestHandlerI defaultHandler = new NoopRequestHandler();

	private HttpRequestHandlerI invalidContextPathHandler = new NoopRequestHandler();

	private Path contextPath;

	/**
	 * @return the contextPath
	 */
	public Path getContextPath() {
		return contextPath;
	}

	public SimpleDispatchingRequestHandler(Path contextPath) {
		this.contextPath = contextPath;
	}

	public SimpleDispatchingRequestHandler addHandler(Path path,
			HttpRequestHandlerI hdl) {
		this.handlerMap.put(path, hdl);
		return this;
	}

	@Override
	public void messageReceived(HttpRequestContextI req) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("messageReceived," + req);

		}
		String uri = req.getRequest().getUri();
		Path p = Path.valueOf(uri);

		HttpRequestHandlerI hdl = null;
		if (!p.isStartWith(this.contextPath)) {
			hdl = this.invalidContextPathHandler;
		} else {//
			p = p.subPath(contextPath.length());
			hdl = this.handlerMap.get(p);
			if (hdl == null) {
				hdl = this.defaultHandler;
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("forwarding request to handler:" + hdl);
		}
		hdl.messageReceived(req);

	}

}
