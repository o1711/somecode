/**
 * 
 */
package com.graphscape.gwt.whosegraph;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.EventBusI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.reflect.InstanceOf;
import com.graphscape.gwt.core.reflect.InstanceOf.CheckerSupport;
import com.graphscape.gwt.core.spi.GwtSPI;
import com.graphscape.gwt.whosegraph.provider.default_.handler.action.ModuleListRefreshAH;

/**
 * @author wuzhen
 * 
 */
public class WhoseGraphGwtServiceProvider implements GwtSPI {

	@Override
	public void active(ContainerI c) {

		ClientI client = c.get(ClientI.class, true);//
		this.activeInstanceOfChecker(c, client);
		this.activeActionHandlers(c, client);

	}

	private void activeInstanceOfChecker(ContainerI c, ClientI client) {
		InstanceOf.addChecker(new CheckerSupport(MainControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MainControlI;
			}
		});
	}

	private void activeActionHandlers(ContainerI c, ClientI client) {
		EventBusI eb = client.getEventBus(true);

		eb.addHandler(AppActions.A_REFRESH_MODULE_LIST,
				new ModuleListRefreshAH(c));//
	}

}
