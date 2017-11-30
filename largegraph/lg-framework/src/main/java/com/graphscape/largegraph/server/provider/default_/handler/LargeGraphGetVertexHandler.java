/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server.provider.default_.handler;

import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.server.Request;
import com.graphscape.largegraph.server.RequestContext;
import com.graphscape.largegraph.server.support.RequestHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 *         <p>
 *         Synchronized response
 */
public class LargeGraphGetVertexHandler extends RequestHandlerSupport {

	@Override
	protected void handleInternal(RequestContext mc) {

		Request se = mc.getRequest();
		// NOTE, the sub message carried out by the outer message
		String id = (String) se.getPayload("id", true);

		MessageI rt = mc.getOutputData();

		VertexI vt = mc.getLargeGraph().getVertex(id);

		rt.setPayload("isNull", vt == null);
		if (vt != null) {
			rt.setPayload("id", vt.getId());
			rt.setPayload("properties", DefaultProperties.valueOf(vt.getAsMap()));
		}

	}
}
