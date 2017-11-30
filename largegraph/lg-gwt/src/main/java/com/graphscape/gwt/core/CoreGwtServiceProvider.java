/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.core;

import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.config.ConfigurationFactoryI;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.endpoint.EndPointI;
import com.graphscape.gwt.core.provider.default_.RootWImpl;
import com.graphscape.gwt.core.provider.default_.UiClientImpl;
import com.graphscape.gwt.core.provider.default_.WidgetFactoryImpl;
import com.graphscape.gwt.core.provider.default_.WindowImpl;
import com.graphscape.gwt.core.provider.default_.config.ConfigurationFactoryImpl;
import com.graphscape.gwt.core.provider.default_.scheduler.SchedulerImpl;
import com.graphscape.gwt.core.reflect.InstanceOf;
import com.graphscape.gwt.core.reflect.InstanceOf.CheckerSupport;
import com.graphscape.gwt.core.scheduler.SchedulerI;
import com.graphscape.gwt.core.spi.GwtSPI;
import com.graphscape.gwt.core.support.WidgetCreaterSupport;

/**
 * @author wu
 * 
 */
public class CoreGwtServiceProvider implements GwtSPI {

	/* */
	@Override
	public void active(ContainerI c) {

		this.activeInstanceOfChecker();

		c.add(new SchedulerImpl(c));
		// window
		WindowI window = new WindowImpl(c);
		c.add(window);
		//
		this.activeWidgetAndFactory(c);

		// RootI widget
		WidgetFactoryI wf = c.get(WidgetFactoryI.class, true);

		RootI root = wf.create(RootI.class);// TODO
		// root
		// model
		c.add(root);// TODO move to SPI.active();

		// client
		ClientI client = new UiClientImpl(c, root);
		c.add(client);
		//
		// message df

		//
		this.activeConfigFactory(c);

		//

	}

	protected void activeConfigFactory(ContainerI c) {

		ConfigurationFactoryI cf = new ConfigurationFactoryImpl(c);
		ClientI client = c.get(ClientI.class, true);
		cf.parent(client);//
	}

	protected void activeWidgetAndFactory(ContainerI c) {
		WidgetFactoryI wf = new WidgetFactoryImpl(c);
		c.add(wf);//
		wf.addCreater(new WidgetCreaterSupport<RootI>(RootI.class) {

			@Override
			public RootI create(ContainerI c, String name, UiPropertiesI<Object> pts) {
				return new RootWImpl(c, name);
			}
		});
	}

	protected void activeInstanceOfChecker() {

		InstanceOf.addChecker(new CheckerSupport(UiObjectI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UiObjectI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(ModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ModelI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(RootI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof RootI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(ClientI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ClientI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(WidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WidgetI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(WidgetFactoryI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WidgetFactoryI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(ConfigurationFactoryI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ConfigurationFactoryI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(EventBusI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EventBusI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(WindowI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WindowI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(UiObjectI.AttacherI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UiObjectI.AttacherI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(EndPointI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EndPointI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(SchedulerI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof SchedulerI;
			}
		});

	}

}
