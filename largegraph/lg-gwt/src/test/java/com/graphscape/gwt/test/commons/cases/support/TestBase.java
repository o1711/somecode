/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.test.commons.cases.support;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.graphscape.gwt.commons.CommonsGwtServiceProvider;
import com.graphscape.gwt.commons.mvc.ControlManagerI;
import com.graphscape.gwt.core.ClientLoader;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.RootI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.CoreGwtServiceProvider;
import com.graphscape.gwt.core.WidgetFactoryI;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.Event.SyncHandlerI;
import com.graphscape.gwt.core.event.AfterClientStartEvent;
import com.graphscape.gwt.core.event.AttachedEvent;
import com.graphscape.gwt.core.event.BeforeClientStartEvent;
import com.graphscape.gwt.core.spi.GwtSPI;

/**
 * @author wuzhen
 * 
 */
public class TestBase extends GWTTestCase {
	protected GwtSPI.Factory factory;

	protected ClientI client;

	protected ContainerI container;

	protected ControlManagerI manager;

	protected WidgetFactoryI wf;

	protected RootI root;

	protected int timeoutMillis = 10 * 1000;

	protected boolean disableWebSocket;

	protected Set<String> finishing = new HashSet<String>();

	protected boolean start = true;

	//protected ProtocolPort PP = new ProtocolPort("http", WebServerTestSupport.WEB_PORT);
	//NOTE,firefox used by gwt test case not support ws
	// new
	// ProtocolPort("ws",8080);
	
	public TestBase(){
		this(false);
	}
	
	public TestBase(boolean start){
		this.start = start;
	}
	@Override
	public String getModuleName() {

		return "com.graphscape.topology.client.test.gwt.UiCommonsImplTest";
	}

	/* */
	@Override
	protected void gwtSetUp() throws Exception {

		super.gwtSetUp();

		GwtSPI[] spis = new GwtSPI[] { GWT.create(CoreGwtServiceProvider.class), GWT.create(CommonsGwtServiceProvider.class) };
		ClientLoader loader = com.graphscape.gwt.core.ClientLoader.getInstance();
		//loader.protocolPort(PP);// testting port.TODO configurable.
		factory = loader.getOrLoadClient(spis, new SyncHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				TestBase.this.onEvent(e);
			}
		});

		this.container = this.factory.getContainer();
		this.client = this.container.get(ClientI.class, true);

		this.wf = this.container.get(WidgetFactoryI.class, true);
		this.root = this.container.get(ClientI.class, true).getRoot();
		this.manager = this.client.getChild(ControlManagerI.class, true); // see
		// xxxSPI.active
		// this.client.attach();//
		if (this.start) {
			this.client.start();//

		}
	}

	protected void onEvent(Event e) {
		System.out.println(this.getClass().getName() + ":" + e);
		if (e instanceof AfterClientStartEvent) {
			this.onClientStart((AfterClientStartEvent) e);
		} else if (e instanceof BeforeClientStartEvent) {
			this.beforeClientStart((BeforeClientStartEvent) e);
		} else if (e instanceof AttachedEvent) {
			this.onAttachedEvent((AttachedEvent) e);
		}
	}

	/**
	 * Jan 2, 2013
	 */
	protected void onAttachedEvent(AttachedEvent e) {

	}

	protected void beforeClientStart(BeforeClientStartEvent e) {

	}

	protected void onClientStart(AfterClientStartEvent e) {

	}

	/* */
	@Override
	protected void gwtTearDown() throws Exception {

		super.gwtTearDown();

	}

	public void dump() {
		System.out.println(this.root.dump());
		System.out.println(this.client.dump());

	}

	public void tryFinish(String item) {
		this.finishing.remove(item);
		System.out.println(item + " done,remains:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}

	}

}
