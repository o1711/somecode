/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 28, 2013
 */
package com.graphscape.gwt.core.endpoint;

import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.data.message.MessageData;

/**
 * @author wu
 * 
 */
public interface MessageCacheI extends UiObjectI {

	public void addMessage(MessageData md);

	public void addMessage(MsgWrapper mw);

	public MessageData getMessage(String id);
	
	public MessageData removeMessage(String id);
	
	public int size();

	public void start();

	public void stop();

}
