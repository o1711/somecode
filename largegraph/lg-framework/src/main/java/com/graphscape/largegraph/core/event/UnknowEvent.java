/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core.event;

import com.graphscape.commons.message.MessageI;
import com.graphscape.largegraph.core.Event;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class UnknowEvent extends Event {

	/**
	 * @param tgt
	 */
	public UnknowEvent(MessageI tgt) {
		super(tgt);
	}

}
