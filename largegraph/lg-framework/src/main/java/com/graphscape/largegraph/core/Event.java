/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core;

import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.MessageWrapper;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class Event extends MessageWrapper {

	/**
	 * @param tgt
	 */
	public Event(MessageI tgt) {
		super(tgt);
	}

	public Event(Path path) {
		super(path);
	}

}
