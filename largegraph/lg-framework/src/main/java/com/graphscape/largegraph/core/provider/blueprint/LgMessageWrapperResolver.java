/**
 * 
 */
package com.graphscape.largegraph.core.provider.blueprint;

import com.graphscape.commons.message.provider.default_.SimpleMessageWrapperResolver;
import com.graphscape.largegraph.core.Constants;
import com.graphscape.largegraph.core.event.EdgeEvent;
import com.graphscape.largegraph.core.event.VertexEvent;

/**
 * @author wuzhen
 * 
 */
public class LgMessageWrapperResolver extends SimpleMessageWrapperResolver {

	// TODO add cache

	public LgMessageWrapperResolver() {

		this.addEventType(Constants.P_EVENTS_VERTEX, VertexEvent.class);
		this.addEventType(Constants.P_EVENTS_EDGE, EdgeEvent.class);

	}

}
