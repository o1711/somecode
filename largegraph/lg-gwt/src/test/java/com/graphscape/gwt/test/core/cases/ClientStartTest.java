/**
 * All right is from Author of the file,to be explained in comming days.
 * Jun 16, 2013
 */
package com.graphscape.gwt.test.core.cases;

import com.graphscape.gwt.core.event.AfterClientStartEvent;
import com.graphscape.gwt.test.core.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ClientStartTest extends TestBase {

	public void testClientStart(){
		
		this.delayTestFinish(timeoutMillis);
		this.client.start();
	}
	@Override
	protected void onClientStart(AfterClientStartEvent e) {
		this.finishTest();//
	}
}
