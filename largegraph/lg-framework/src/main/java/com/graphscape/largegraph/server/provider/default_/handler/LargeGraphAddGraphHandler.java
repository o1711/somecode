/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server.provider.default_.handler;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.server.Request;
import com.graphscape.largegraph.server.RequestContext;
import com.graphscape.largegraph.server.support.RequestHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 *         <p>
 *         Synchronized response
 */
public class LargeGraphAddGraphHandler extends RequestHandlerSupport {

	@Override
	protected void handleInternal(RequestContext mc) {

		Request se = mc.getRequest();

		PropertiesI<Object> msg = (PropertiesI<Object>) se.getPayload("properties", true);
		MessageI rt = mc.getOutputData();

		GraphI g = mc.getGraph();// current graph.//TODO check permission of add
									// graph?

		GraphI gr = mc.getLargeGraph().addGraph(msg);

		rt.setPayload("isNull", false);
		rt.setPayload("id", gr.getId());
		rt.setPayload("properties", DefaultProperties.valueOf(gr.getAsMap()));

	}
}
