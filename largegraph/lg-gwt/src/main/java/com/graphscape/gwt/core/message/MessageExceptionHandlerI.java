/**
 *  Dec 24, 2012
 */
package com.graphscape.gwt.core.message;

import com.graphscape.gwt.core.HandlerI;
import com.graphscape.gwt.core.message.MessageException;

/**
 * @author wuzhen
 * 
 */
public interface MessageExceptionHandlerI extends HandlerI<MessageException> {

	public void handle(MessageException me);

}
