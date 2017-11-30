/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server.provider.default_.handler;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.server.Request;
import com.graphscape.largegraph.server.RequestContext;
import com.graphscape.largegraph.server.support.RequestHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 *         <p>
 *         Synchronized response
 */
public class LargeGraphAddEdgeHandler extends RequestHandlerSupport {

	@Override
	protected void handleInternal(RequestContext mc) {

		Request se = mc.getRequest();
		// NOTE, the sub message carried out by the outer message
		PropertiesI<Object> msg = (PropertiesI<Object>) se.getPayload("properties", false);
		
		MessageI rt = mc.getOutputData();
		String label = (String)se.getPayload("label",true);
		String sId = (String)se.getPayload("startVertexId",true);
		String eId = (String)se.getPayload("endVertexId",true);
		
		EdgeI ed = mc.getLargeGraph().addEdge(Label.valueOf(label), sId, eId);
		
		rt.setPayload("isNull", false);
		rt.setPayload("id", ed.getId());
		rt.setPayload("label", ed.getLabel().toString());
		rt.setPayload("startVertexId", ed.getTailVertex().getId());
		rt.setPayload("endVertexId", ed.getHeadVertex().getId());		
		rt.setPayload("properties", DefaultProperties.valueOf(ed.getAsMap()));

	}
}
