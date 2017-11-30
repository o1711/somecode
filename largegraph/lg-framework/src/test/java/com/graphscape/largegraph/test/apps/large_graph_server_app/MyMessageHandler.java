/**
 * Dec 13, 2013
 */
package com.graphscape.largegraph.test.apps.large_graph_server_app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.core.Arrow;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.EventContext;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.event.VertexEvent;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class MyMessageHandler implements HandlerI<EventContext> {

	// TODO loop detect
	@Override
	public void handle(EventContext mc) {

		LargeGraphI lg = mc.getLargeGraph();
		VertexEvent ve = mc.getEvent();
		String vid = ve.getVertexId();

		VertexI vt = mc.getLargeGraph().getVertex(vid);

		Iterator<EdgeI> it = vt.getEdgeIterator(Arrow.TAIL, LargeGraphServerAppTest.MYEDGE);

		boolean isLeaf = !it.hasNext();
		List<String> leafIdL = new ArrayList<String>();

		if (isLeaf) {
			leafIdL.add(vid);
		} else {

			while (it.hasNext()) {
				EdgeI eI = it.next();
				VertexI vI = eI.getHeadVertex();

				MessageI mI = new VertexEvent(vI.getId());

				FutureI<MessageI> msg = lg.putMessage(mI);
				MessageI rtI = msg.get(TimeAndUnit.valueOf(10, TimeUnit.SECONDS));
				List<String> leafIdIL = (List<String>) rtI.getPayload("leafIdList");
				leafIdL.addAll(leafIdIL);//

			}

		}
		mc.getOutputData().setPayload("leafIdList", leafIdL);
	}

}
