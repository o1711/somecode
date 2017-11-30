/**
 * Jul 1, 2012
 */
package com.graphscape.gwt.commons;

import com.graphscape.gwt.commons.drag.DraggerI;
import com.graphscape.gwt.commons.editor.basic.BooleanEditorI;
import com.graphscape.gwt.commons.editor.basic.DateEditorI;
import com.graphscape.gwt.commons.editor.basic.EnumEditorI;
import com.graphscape.gwt.commons.editor.basic.IntegerEditorI;
import com.graphscape.gwt.commons.editor.basic.StringEditorI;
import com.graphscape.gwt.commons.editor.image.ImageCropEditorI;
import com.graphscape.gwt.commons.editor.image.ImageFileUrlDataEditorI;
import com.graphscape.gwt.commons.editor.properties.PropertiesEditorI;
import com.graphscape.gwt.commons.editor.properties.PropertiesEditorI.PropertyModel;
import com.graphscape.gwt.commons.event.AutoLoginRequireEvent;
import com.graphscape.gwt.commons.event.HeaderItemEvent;
import com.graphscape.gwt.commons.event.UserLoginEvent;
import com.graphscape.gwt.commons.frwk.BodyModelI;
import com.graphscape.gwt.commons.frwk.BodyViewI;
import com.graphscape.gwt.commons.frwk.BottomViewI;
import com.graphscape.gwt.commons.frwk.ConsoleViewI;
import com.graphscape.gwt.commons.frwk.FrwkControlI;
import com.graphscape.gwt.commons.frwk.FrwkViewI;
import com.graphscape.gwt.commons.frwk.HeaderModelI;
import com.graphscape.gwt.commons.frwk.HeaderViewI;
import com.graphscape.gwt.commons.frwk.ViewReferenceI;
import com.graphscape.gwt.commons.frwk.WindowModelI;
import com.graphscape.gwt.commons.frwk.blank.BlankModelI;
import com.graphscape.gwt.commons.frwk.commons.FieldModel;
import com.graphscape.gwt.commons.frwk.commons.FormModel;
import com.graphscape.gwt.commons.frwk.commons.LineModel;
import com.graphscape.gwt.commons.frwk.login.LoginControlI;
import com.graphscape.gwt.commons.frwk.login.LoginModelI;
import com.graphscape.gwt.commons.frwk.login.LoginViewI;
import com.graphscape.gwt.commons.mvc.ActionModelI;
import com.graphscape.gwt.commons.mvc.ControlI;
import com.graphscape.gwt.commons.mvc.ControlManagerI;
import com.graphscape.gwt.commons.mvc.ViewI;
import com.graphscape.gwt.commons.provider.default_.drag.DraggerImpl;
import com.graphscape.gwt.commons.provider.default_.editor.basic.BooleanEditorImpl;
import com.graphscape.gwt.commons.provider.default_.editor.basic.DateEditorImpl;
import com.graphscape.gwt.commons.provider.default_.editor.basic.EnumEditorImpl;
import com.graphscape.gwt.commons.provider.default_.editor.basic.IntegerEditorImpl;
import com.graphscape.gwt.commons.provider.default_.editor.basic.StringEditorImpl;
import com.graphscape.gwt.commons.provider.default_.editor.file.ImageFileUrlDataEditorImpl;
import com.graphscape.gwt.commons.provider.default_.editor.image.ImageCropEditorImpl;
import com.graphscape.gwt.commons.provider.default_.editor.properties.PropertiesEditorImpl;
import com.graphscape.gwt.commons.provider.default_.frwk.FrwkControlImpl;
import com.graphscape.gwt.commons.provider.default_.frwk.commons.form.FormView;
import com.graphscape.gwt.commons.provider.default_.frwk.commons.form.FormsView;
import com.graphscape.gwt.commons.provider.default_.frwk.header.HeaderView;
import com.graphscape.gwt.commons.provider.default_.frwk.login.LoginControlImpl;
import com.graphscape.gwt.commons.provider.default_.frwk.login.LoginView;
import com.graphscape.gwt.commons.provider.default_.handler.ClientStartedHandler;
import com.graphscape.gwt.commons.provider.default_.handler.EndpointBondHandler;
import com.graphscape.gwt.commons.provider.default_.handler.LoginHeaderItemHandler;
import com.graphscape.gwt.commons.provider.default_.handler.LogoutHeaderItemHandler;
import com.graphscape.gwt.commons.provider.default_.handler.UserLoginHandler;
import com.graphscape.gwt.commons.provider.default_.handler.action.AutoLoginHandler;
import com.graphscape.gwt.commons.provider.default_.handler.action.LoginSubmitAH;
import com.graphscape.gwt.commons.provider.default_.handler.action.PasswordForgotAP;
import com.graphscape.gwt.commons.provider.default_.handler.action.PasswordResetAP;
import com.graphscape.gwt.commons.provider.default_.mvc.ControlManagerImpl;
import com.graphscape.gwt.commons.provider.default_.widget.bar.BarWidgetImpl;
import com.graphscape.gwt.commons.provider.default_.widget.basic.AnchorWImpl;
import com.graphscape.gwt.commons.provider.default_.widget.basic.ButtonImpl;
import com.graphscape.gwt.commons.provider.default_.widget.basic.DateWImpl;
import com.graphscape.gwt.commons.provider.default_.widget.basic.ImageImpl;
import com.graphscape.gwt.commons.provider.default_.widget.basic.LabelImpl;
import com.graphscape.gwt.commons.provider.default_.widget.error.ErrorInfosWidgetImpl;
import com.graphscape.gwt.commons.provider.default_.widget.html.HtmlElementWidgetImpl;
import com.graphscape.gwt.commons.provider.default_.widget.list.ListImpl;
import com.graphscape.gwt.commons.provider.default_.widget.menu.MenuWImpl;
import com.graphscape.gwt.commons.provider.default_.widget.panel.PanelWImpl;
import com.graphscape.gwt.commons.provider.default_.widget.stack.StackWImpl;
import com.graphscape.gwt.commons.provider.default_.widget.tab.TabberWImpl;
import com.graphscape.gwt.commons.provider.default_.widget.table.TableImpl;
import com.graphscape.gwt.commons.provider.default_.widget.wpanel.WindowPanelWImpl;
import com.graphscape.gwt.commons.widget.EditorI;
import com.graphscape.gwt.commons.widget.bar.BarWidgetI;
import com.graphscape.gwt.commons.widget.basic.AnchorWI;
import com.graphscape.gwt.commons.widget.basic.ButtonI;
import com.graphscape.gwt.commons.widget.basic.DateWI;
import com.graphscape.gwt.commons.widget.basic.ImageI;
import com.graphscape.gwt.commons.widget.basic.LabelI;
import com.graphscape.gwt.commons.widget.error.ErrorInfosWidgetI;
import com.graphscape.gwt.commons.widget.html.HtmlElementWidgetI;
import com.graphscape.gwt.commons.widget.list.ListI;
import com.graphscape.gwt.commons.widget.menu.MenuItemWI;
import com.graphscape.gwt.commons.widget.menu.MenuWI;
import com.graphscape.gwt.commons.widget.panel.PanelWI;
import com.graphscape.gwt.commons.widget.popup.PopupWI;
import com.graphscape.gwt.commons.widget.stack.StackWI;
import com.graphscape.gwt.commons.widget.tab.TabWI;
import com.graphscape.gwt.commons.widget.tab.TabberWI;
import com.graphscape.gwt.commons.widget.table.TableI;
import com.graphscape.gwt.commons.widget.wpanel.WindowPanelWI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.EventBusI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.WidgetFactoryI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.event.AfterClientStartEvent;
import com.graphscape.gwt.core.event.EndpointBondEvent;
import com.graphscape.gwt.core.reflect.InstanceOf;
import com.graphscape.gwt.core.reflect.InstanceOf.CheckerSupport;
import com.graphscape.gwt.core.spi.GwtSPI;
import com.graphscape.gwt.core.support.WidgetCreaterSupport;

/**
 * @author wu
 * 
 */
public class CommonsGwtServiceProvider implements GwtSPI {

	/* */
	@Override
	public void active(ContainerI c) {
		final ClientI client = c.get(ClientI.class, true);

		this.activeInstaneOf(c);

		this.activeWidgetCreater(c);

		// note the console view must be after instanceof and widget creater
		// registered.

		//
		this.activeActionHandlers(c, client);

		this.activeOtherHandlers(c, client);
		//
		new DraggerImpl(c).parent(client);

		//

		// controls
		ControlManagerI manager = new ControlManagerImpl(c);
		manager.parent(client);
		manager.child(new FrwkControlImpl(c, "frwk"));
		manager.child(new LoginControlImpl(c, "login"));

	}

	public void activeActionHandlers(ContainerI c, ClientI client) {
		EventBusI eb = client.getEventBus(true);

		eb.addHandler(Actions.A_LOGIN_SUBMIT, new LoginSubmitAH(c));

		eb.addHandler(Actions.A_PASSWORD_FORGOT, new PasswordForgotAP(c));
		eb.addHandler(Actions.A_PASSWORD_RESET, new PasswordResetAP(c));

	}

	public void activeOtherHandlers(ContainerI c, ClientI client) {
		EventBusI eb = client.getEventBus(true);
		eb.addHandler(AfterClientStartEvent.TYPE, new ClientStartedHandler(c));// NOTE
		eb.addHandler(EndpointBondEvent.TYPE, new EndpointBondHandler(c));
		eb.addHandler(
				HeaderItemEvent.TYPE.getAsPath().concat(HeaderItems.USER_LOGIN),
				new LoginHeaderItemHandler(c));
		eb.addHandler(
				HeaderItemEvent.TYPE.getAsPath()
						.concat(HeaderItems.USER_LOGOUT),
				new LogoutHeaderItemHandler(c));

		eb.addHandler(UserLoginEvent.TYPE, new UserLoginHandler(c));
		eb.addHandler(AutoLoginRequireEvent.TYPE, new AutoLoginHandler(c));
	}

	public void activeWidgetCreater(ContainerI c) {
		WidgetFactoryI wf = c.get(WidgetFactoryI.class, true);
		//
		wf.addCreater(new WidgetCreaterSupport<AnchorWI>(AnchorWI.class) {
			@Override
			public AnchorWI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new AnchorWImpl(c, name);

			}
		});
		//
		wf.addCreater(new WidgetCreaterSupport<ListI>(ListI.class) {
			@Override
			public ListI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new ListImpl(c, name, pts);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<HtmlElementWidgetI>(
				HtmlElementWidgetI.class) {
			@Override
			public HtmlElementWidgetI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new HtmlElementWidgetImpl(c, name, pts);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<PanelWI>(PanelWI.class) {
			@Override
			public PanelWI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new PanelWImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<StackWI>(StackWI.class) {
			@Override
			public StackWI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new StackWImpl(c, name, pts);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<TabberWI>(TabberWI.class) {
			@Override
			public TabberWI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new TabberWImpl(c, name, pts);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<TableI>(TableI.class) {
			@Override
			public TableI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new TableImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ImageI>(ImageI.class) {
			@Override
			public ImageI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new ImageImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ButtonI>(ButtonI.class) {
			@Override
			public ButtonI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new ButtonImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<LabelI>(LabelI.class) {
			@Override
			public LabelI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new LabelImpl(c, name);

			}
		});
		//
		wf.addCreater(new WidgetCreaterSupport<MenuWI>(MenuWI.class) {
			@Override
			public MenuWI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new MenuWImpl(c, name);

			}
		});

		// Editors

		wf.addCreater(new WidgetCreaterSupport<StringEditorI>(
				StringEditorI.class) {
			@Override
			public StringEditorI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new StringEditorImpl(c, name, pts);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<BooleanEditorI>(
				BooleanEditorI.class) {
			@Override
			public BooleanEditorI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new BooleanEditorImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<IntegerEditorI>(
				IntegerEditorI.class) {
			@Override
			public IntegerEditorI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new IntegerEditorImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<DateEditorI>(DateEditorI.class) {
			@Override
			public DateEditorI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new DateEditorImpl(c, name, pts);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<PropertiesEditorI>(
				PropertiesEditorI.class) {
			@Override
			public PropertiesEditorI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new PropertiesEditorImpl(c, name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<WindowPanelWI>(
				WindowPanelWI.class) {
			@Override
			public WindowPanelWI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new WindowPanelWImpl(c, name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<BarWidgetI>(BarWidgetI.class) {
			@Override
			public BarWidgetI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new BarWidgetImpl(c, name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<EnumEditorI>(EnumEditorI.class) {
			@Override
			public EnumEditorI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new EnumEditorImpl(c, name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<ErrorInfosWidgetI>(
				ErrorInfosWidgetI.class) {
			@Override
			public ErrorInfosWidgetI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new ErrorInfosWidgetImpl(c, name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ImageFileUrlDataEditorI>(
				ImageFileUrlDataEditorI.class) {
			@Override
			public ImageFileUrlDataEditorI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new ImageFileUrlDataEditorImpl(c, name, pts);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ImageCropEditorI>(
				ImageCropEditorI.class) {
			@Override
			public ImageCropEditorI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new ImageCropEditorImpl(c, name, pts);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<DateWI>(DateWI.class) {
			@Override
			public DateWI create(ContainerI c, String name,
					UiPropertiesI<Object> pts) {

				return new DateWImpl(c, name);

			}
		});

	}

	public void activeInstaneOf(ContainerI c) {
		InstanceOf.addChecker(new CheckerSupport(AnchorWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof AnchorWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ActionModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ActionModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ButtonI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ButtonI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ControlI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ControlManagerI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ControlManagerI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BodyModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BodyModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(HeaderModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(TabberWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof TabberWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(TabWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof TabWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(StackWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof StackWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PanelWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PanelWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ImageI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ImageI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(ViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(WidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WidgetI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(EditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PropertiesEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PropertiesEditorI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(BooleanEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BooleanEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(IntegerEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof IntegerEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(DateEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof DateEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(MenuWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MenuWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(MenuItemWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MenuItemWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(StringEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof StringEditorI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(WindowPanelWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WindowPanelWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ConsoleViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ConsoleViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(WindowModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WindowModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(LineModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LineModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PropertyModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PropertyModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FieldModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FieldModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(AdjusterI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof AdjusterI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FormModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FormModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FormView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FormView;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FormsView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FormsView;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(LoginModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(LoginView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginView;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BlankModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BlankModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(LoginControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginControlI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(HeaderView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderView;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(HeaderModelI.ItemModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderModelI.ItemModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BarWidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BarWidgetI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ViewReferenceI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewReferenceI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(EnumEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EnumEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ErrorInfosWidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ErrorInfosWidgetI;
			}
		});
		InstanceOf
				.addChecker(new CheckerSupport(ImageFileUrlDataEditorI.class) {

					@Override
					public boolean isInstance(Object o) {

						return o instanceof ImageFileUrlDataEditorI;
					}
				});
		InstanceOf.addChecker(new CheckerSupport(ImageCropEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ImageCropEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(DraggerI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof DraggerI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FrwkControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FrwkControlI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PopupWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PopupWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(DateWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof DateWI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(FrwkViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FrwkViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BodyViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BodyViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(HeaderViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderViewI;
			}
		});
		
		InstanceOf.addChecker(new CheckerSupport(LoginViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginViewI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(BottomViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BottomViewI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(HtmlElementWidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HtmlElementWidgetI;

			}

		});
	}

}
