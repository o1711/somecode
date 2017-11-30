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
public class CometListenerAdaptor implements CometListenerI {

	protected static final Logger LOG = LoggerFactory.getLogger(CometListenerAdaptor.class);

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

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.api.CometListenerI#onConnect(com.fs.commons.webcomet.api.CometI)
	 */
	@Override
	public void onConnect(CometI ws) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.api.CometListenerI#onMessage(com.fs.commons.webcomet.api.CometI, java.lang.String)
	 */
	@Override
	public void onMessage(CometI ws, Object ms) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.api.CometListenerI#onException(com.fs.commons.webcomet.api.CometI, java.lang.Throwable)
	 */
	@Override
	public void onException(CometI ws, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.fs.commons.webcomet.api.CometListenerI#onClose(com.fs.commons.webcomet.api.CometI, int, java.lang.String)
	 */
	@Override
	public void onClose(CometI ws, int statusCode, String reason) {
		// TODO Auto-generated method stub
		
	}

}
