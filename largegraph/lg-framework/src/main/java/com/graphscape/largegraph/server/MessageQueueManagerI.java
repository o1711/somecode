/**
 * Dec 15, 2013
 */
package com.graphscape.largegraph.server;

import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.other.IdBasedManagerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface MessageQueueManagerI extends IdBasedManagerI<FiniteBlockingQueueI<MessageI>> {

}
