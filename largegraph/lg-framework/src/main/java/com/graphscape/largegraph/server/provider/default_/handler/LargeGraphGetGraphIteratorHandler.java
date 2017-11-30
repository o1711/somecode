/**
 * Dec 16, 2013
 */
package com.graphscape.largegraph.server.provider.default_.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class LargeGraphGetGraphIteratorHandler extends RequestHandlerSupport {

	@Override
	protected void handleInternal(RequestContext mc) {

		Request se = mc.getRequest();
		// NOTE, the sub message carried out by the outer message
		String id = (String) se.getPayload("vertexId", true);

		MessageI rt = mc.getOutputData();

		Iterator<GraphI> vIt = mc.getLargeGraph().getVertex(id).getGraphIterator();

		rt.setPayload("isNull", false);
		List<PropertiesI<Object>> vL = new ArrayList<PropertiesI<Object>>();
		rt.setPayload("list", vL);//
		while (vIt.hasNext()) {
			GraphI vt = vIt.next();
			PropertiesI<Object> ptI = new DefaultProperties<Object>();

			ptI.setProperty("id", vt.getId());
			ptI.setProperty("properties", DefaultProperties.valueOf(vt.getAsMap()));
			vL.add(ptI);
		}

	}
}
