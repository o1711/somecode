/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk;

import com.google.gwt.user.client.Window;
import com.graphscape.gwt.commons.frwk.BodyViewI;
import com.graphscape.gwt.commons.mvc.simple.LightWeightView;
import com.graphscape.gwt.commons.widget.panel.PanelWI;
import com.graphscape.gwt.commons.widget.tab.TabWI;
import com.graphscape.gwt.commons.widget.tab.TabberWI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.support.MapProperties;

/**
 * @author wu
 * 
 */
public class BodyView extends LightWeightView implements BodyViewI {

	private TabberWI tabber;// TODO replace by a stack and a menu view.

	// private Map<Path, WidgetI> itemMap = new HashMap<Path, WidgetI>();

	/**
	 * @param ctn
	 */
	public BodyView(ContainerI c) {
		super(c, "body");
		UiPropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperty(TabberWI.PK_IS_VERTICAL, Boolean.TRUE);
		pts.setProperty(TabberWI.PK_IS_CLOSABLE, Boolean.TRUE);
		pts.setProperty(TabberWI.PK_IS_REVERSE, Boolean.TRUE);
		this.tabber = this.factory.create(TabberWI.class, this.getChildName("tabber"), pts);//
		this.tabber.parent(this);

	}

	/**
	 * @param t
	 */
	public <T extends WidgetI> T addItem(Path path, T w) {
		WidgetI old = this.getItem(path, false);

		if (old != null) {
			throw new UiException("already exist:" + path + ",widget:" + old);
		}
		final PanelWI prt = this.factory.create(PanelWI.class);
		final TabWI sitem = this.tabber.addTab(path, prt);
		w.parent(prt);
		// this.itemMap.put(path, w);
		return w;
	}

	/**
	 * @param t
	 */
	@Override
	public void select(Path path) {

		final TabWI sitem = this.tabber.getTab(path, true);
		sitem.select();
		//TODO animate
		Window.scrollTo(0, 0);
	}

	/**
	 * @param b
	 */
	public <T extends WidgetI> T getItem(Path path, boolean force) {
		TabWI ta = this.getTabOfItem(path, false);
		if (ta == null) {
			if (force) {
				throw new UiException("no item found :" + path);
			}
			return null;
		}
		PanelWI p = (PanelWI) ta.getManaged();
		return (T) p.getChildWidgetList().get(0);//

	}

	protected TabWI getTabOfItem(Path path, boolean force) {
		TabWI ta = this.tabber.getTab(path, false);
		if (ta == null) {
			if (force) {
				throw new UiException("no item found :" + path);
			}
			return null;
		}
		return ta;
	}

	@Override
	public <T extends WidgetI> T getOrCreateItem(Path path, com.graphscape.gwt.commons.CreaterI<T> crt) {
		return this.getOrCreateItem(path, crt, false);
	}

	@Override
	public <T extends WidgetI> T getOrCreateItem(Path path, com.graphscape.gwt.commons.CreaterI<T> crt,
			boolean select) {
		T rt = this.getItem(path, false);
		if (rt == null) {
			rt = crt.create(this.getContainer());
			this.addItem(path, rt);
		}
		if (select) {
			this.select(path);
		}
		return rt;
	}

	@Override
	public void setTitleOfItem(Path path, String title, boolean force) {
		TabWI tab = this.getTabOfItem(path, false);
		if (tab == null) {
			if (force) {
				throw new UiException("no item found:" + path);
			}
		}
		tab.setText(false, title);

	}

	/*
	 * Mar 16, 2013
	 */
	@Override
	public void tryCloseItem(Path path) {
		//

		this.tabber.remove(path);

	}

	/*
	 * Mar 21, 2013
	 */
	@Override
	public void closeAllItems() {
		this.tabber.removeAll();
	}

}
