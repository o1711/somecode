/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core.event;

import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.Constants;
import com.graphscape.largegraph.core.Event;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class VertexEvent extends Event {

	public static final String HK_VERTEX_ID = "vertexId";

	public VertexEvent(String vid) {
		super(Constants.P_EVENTS_VERTEX);
		this.setHeader(HK_VERTEX_ID, vid);
	}

	/**
	 * @param tgt
	 */
	public VertexEvent(MessageI tgt) {
		super(tgt);

	}

	public String getVertexId() {
		return this.getHeader("vertexId", true);
	}

}
