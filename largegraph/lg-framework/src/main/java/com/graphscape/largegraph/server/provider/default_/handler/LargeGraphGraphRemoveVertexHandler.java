/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server.provider.default_.handler;

import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.server.Request;
import com.graphscape.largegraph.server.RequestContext;
import com.graphscape.largegraph.server.support.RequestHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 *         <p>
 *         Synchronized response
 */
public class LargeGraphGraphRemoveVertexHandler extends RequestHandlerSupport {

	@Override
	protected void handleInternal(RequestContext mc) {

		Request se = mc.getRequest();
		// NOTE, the sub message carried out by the outer message

		String gId = (String) se.getPayload("graphId", true);

		String vId = (String) se.getPayload("vertexId", true);
		MessageI rt = mc.getOutputData();

		GraphI g = mc.getLargeGraph().getGraph(gId);
		VertexI vt = g.removeVertex(vId);

		rt.setPayload("isNull", vt == null);
		if (vt != null) {
			rt.setPayload("id", vt.getId());
			rt.setPayload("properties", DefaultProperties.valueOf(vt.getAsMap()));
		}

	}
}
