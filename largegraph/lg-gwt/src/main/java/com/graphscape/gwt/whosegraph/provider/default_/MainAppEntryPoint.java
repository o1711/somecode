/**
 * 
 */
package com.graphscape.gwt.whosegraph.provider.default_;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.graphscape.gwt.commons.CommonsGwtServiceProvider;
import com.graphscape.gwt.core.ClientLoader;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.RootI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.CoreGwtServiceProvider;
import com.graphscape.gwt.core.core.Event;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.spi.GwtSPI;
import com.graphscape.gwt.whosegraph.WhoseGraphGwtServiceProvider;

/**
 * @author wuzhen
 * 
 */
public class MainAppEntryPoint implements EntryPoint {
	private ContainerI container;
	private ClientI client;

	/* */
	@Override
	public void onModuleLoad() {
		//
		GwtSPI[] spis = new GwtSPI[] {
				(CoreGwtServiceProvider) GWT.create(CoreGwtServiceProvider.class),
				(CommonsGwtServiceProvider) GWT.create(CommonsGwtServiceProvider.class),
				(WhoseGraphGwtServiceProvider) GWT.create(WhoseGraphGwtServiceProvider.class) };
		GwtSPI.Factory sf = ClientLoader.getInstance().getOrLoadClient(spis,
				new EventHandlerI<Event>() {

					@Override
					public void handle(Event e) {
						// TODO
					}
				});

		this.container = sf.getContainer();
		client = this.container.get(ClientI.class, true);

		client.start();
		RootI root = client.getRoot();

	}

}