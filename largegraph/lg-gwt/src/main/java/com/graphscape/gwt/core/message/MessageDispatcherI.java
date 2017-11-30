/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.graphscape.gwt.core.message;

import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.message.MessageExceptionHandlerI;
import com.graphscape.gwt.core.message.MessageHandlerI;

/**
 * @author wu
 * 
 */
public interface MessageDispatcherI extends MessageHandlerI<MsgWrapper> {

	public <W extends MsgWrapper> void addHandler(Path path,
			MessageHandlerI<W> mh);

	public <W extends MsgWrapper> void addHandler(Path path, boolean strict,
			MessageHandlerI<W> mh);

	public <W extends MsgWrapper> void addDefaultHandler(MessageHandlerI<W> mh);

	public void addExceptionHandler(MessageExceptionHandlerI eh);

	public void dispatch(MsgWrapper mw);

	public void cleanAllHanlders();
}
