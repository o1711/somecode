/**
 *  Dec 11, 2012
 */
package com.fs.websocket.impl.test.cases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.webcomet.impl.test.support.CometTestBase;

/**
 * @author wuzhen
 * 
 */
public class WebSocketTest extends CometTestBase {

	/**
	 * @param protocol
	 */
	public WebSocketTest() {
		super("websocket");
	}

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketTest.class);

	private static final boolean srmac = false;

	public void testClients() throws Exception {
		super.doTestClients();
		
	}

}
