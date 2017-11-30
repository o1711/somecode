/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.test.core.cases.support;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.graphscape.gwt.core.ClientLoader;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.CoreGwtServiceProvider;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.endpoint.EndPointI;
import com.graphscape.gwt.core.event.AfterClientStartEvent;
import com.graphscape.gwt.core.spi.GwtSPI;
import com.graphscape.gwt.test.core.UiCoreTestGPI;

/**
 * @author wu
 * 
 */
public abstract class TestBase extends GWTTestCase {

	protected GwtSPI.Factory factory;

	protected ClientI client;
	protected ContainerI container;

	protected EndPointI endpoint;

	protected Set<String> finishing;

	protected boolean start;
	
	protected int timeoutMillis = 30 * 1000;

	public TestBase() {
		this(false);
	}

	public TestBase(boolean start) {
		this.start = start;
	}

	/* */
	@Override
	public String getModuleName() {

		return "com.graphscape.topology.client.test.gwt.UiCoreImplTest";

	}

	/* */
	@Override
	protected void gwtSetUp() throws Exception {

		super.gwtSetUp();
		
		this.finishing = new HashSet<String>();

		GwtSPI[] spis = new GwtSPI[] { GWT.create(CoreGwtServiceProvider.class), new UiCoreTestGPI() };
		ClientLoader cl = ClientLoader.getInstance();
		factory = cl.getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				TestBase.this.onEvent(e);
			}
		});
		this.container = this.factory.getContainer();
		this.client = this.container.get(ClientI.class, true);
		if (this.start) {
			this.client.start();//
		}

	}

	protected void onEvent(Event e) {
		System.out.println("TestBase.onEvent(),e:" + e);
		if (e instanceof AfterClientStartEvent) {
			AfterClientStartEvent afe = (AfterClientStartEvent)e;
			this.endpoint = afe.getClient().getEndpoint(true);
			this.onClientStart(afe);
			
		}
	}

	public void tryFinish(String finish) {
		this.finishing.remove(finish);
		System.out.println("finish:" + finish + ",waiting:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}
	}

	/**
	 * @param e
	 */
	protected void onClientStart(AfterClientStartEvent e) {

	}
}
