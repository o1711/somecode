/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 12, 2012
 */
package com.graphscape.commons.comet.support;

import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.comet.CometI;
import com.graphscape.commons.comet.CometListenerI;
import com.graphscape.commons.util.StringUtil;

/**
 * @author wu
 * 
 */
public abstract class AbstractCometListener implements CometListenerI {

	protected static final Logger LOG = LoggerFactory.getLogger(AbstractCometListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.websocket.api.WsListenerI#onMessage(com.fs.commons.websocket.api.WebSocketI
	 * , java.io.Reader)
	 */
	@Override
	public void onMessage(CometI ws, Reader reader) {
		String s = StringUtil.readAsString(reader);
		this.onMessage(ws, s);
	}

}
