/**
 * Jun 25, 2012
 */
package com.graphscape.gwt.commons.mvc.support;

import com.graphscape.gwt.commons.CreaterI;
import com.graphscape.gwt.commons.frwk.BodyViewI;
import com.graphscape.gwt.commons.frwk.FrwkViewI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.RootI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wuzhen
 * 
 */
public class ControlSupport extends AbstractControl {

	public ControlSupport(ContainerI c, String name) {
		super(c, name);
	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public void processChildModelRemove(ModelI parent, ModelI child) {
		//

	}

	public <T extends ModelI> T getOrCreateModel(ModelI parent, Class<T> cls, String name, CreaterI<T> crt) {
		T rt = parent.getChild(cls, name, false);
		if (rt != null) {
			return rt;
		}
		rt = crt.create(this.container);
		rt.parent(parent);
		return rt;
	}

	public <T extends ModelI> T getOrCreateModel(ModelI parent, Class<T> cls, CreaterI<T> crt) {
		T rt = parent.getChild(cls, false);
		if (rt != null) {
			return rt;
		}
		rt = crt.create(this.container);
		rt.parent(parent);
		return rt;
	}

	public <T extends WidgetI> T getOrCreateView(WidgetI parent, Class<T> cls, CreaterI<T> crt) {
		T rt = parent.getChild(cls, false);
		if (rt != null) {
			return rt;
		}
		rt = crt.create(this.container);
		rt.parent(parent);
		return rt;
	}
	public <T extends WidgetI> T getOrCreateViewInBody(Path path, CreaterI<T> crt) {
		return this.getOrCreateViewInBody(path, crt, false);
	}
	public <T extends WidgetI> T getOrCreateViewInBody(Path path, CreaterI<T> crt,boolean select) {
		return this.getBodyView().getOrCreateItem(path, crt,select);

	}

	public RootI getRootView() {
		return this.getClient(true).getRoot();
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getEndpoint(true).sendMessage(req);//
	}

	protected FrwkViewI getFrwkView() {
		return this.getRootView().getChild(FrwkViewI.class, true);
	}

	protected BodyViewI getBodyView() {
		return this.getFrwkView().getBodyView();
	}
}
