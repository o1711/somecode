package com.graphscape.largegraph.server;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.LifeCycleI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * 
 * @author wuzhen0808@gmail.com
 * 
 */
public interface LargeGraphServerI extends LifeCycleI {

	/**
	 * State-less message processing
	 * 
	 * @param msg
	 * @return
	 */
	public FutureI<MessageI> putMessage(MessageI msg);

	public LargeGraphI getLargeGraph();

}
