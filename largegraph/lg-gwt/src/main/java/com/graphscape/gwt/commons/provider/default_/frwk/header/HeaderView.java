/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk.header;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.gwt.commons.Position;
import com.graphscape.gwt.commons.frwk.HeaderViewI;
import com.graphscape.gwt.commons.mvc.simple.SimpleView;
import com.graphscape.gwt.commons.provider.default_.frwk.header.ItemView;
import com.graphscape.gwt.commons.widget.bar.BarWidgetI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;

/**
 * @author wu
 * 
 */
public class HeaderView extends SimpleView implements HeaderViewI {

	private BarWidgetI itemList;

	private Map<Path, ItemView> itemViewMap = new HashMap<Path, ItemView>();

	/**
	 * @param ctn
	 */
	public HeaderView(ContainerI c) {
		super(c, "header");
		this.itemList = this.factory.create(BarWidgetI.class);
		this.itemList.parent(this);//
		this.itemList.getElement().addClassName("header-bar");

	}

	public ItemView getOrCreateItem(Path path, boolean preferLeft) {

		ItemView rt = this.getItem(path);
		if (rt != null) {
			return rt;
		}
		rt = new ItemView(this.getContainer(), path);
		Position p = preferLeft ? BarWidgetI.P_LEFT : BarWidgetI.P_RIGHT;
		this.itemList.addItem(p, rt);
		this.itemViewMap.put(path, rt);
		return rt;
	}

	/*
	 *Mar 30, 2013
	 */
	@Override
	public void tryRemoveItem(Path path) {
		ItemView iv = this.getItem(path);
		if(iv == null){
			return;
		}
		iv.parent(null);
		this.itemViewMap.remove(iv);
	}
	public ItemView getItem(Path path){
		ItemView rt = this.itemViewMap.get(path);
		return rt;
	}

	@Override
	public void addItem(Path path) {
		this.addItem(path, false);
	}

	@Override
	public void addItem(Path path, boolean left) {

		if (path.size() == 1) {
			ItemView rt = this.getOrCreateItem(path, left);
		} else if (path.size() == 2) {
			ItemView rt = this.getOrCreateItem(path.getParent(), left);

			rt.getOrAddMenuItem(path.getName());

		} else {
			throw new UiException("not support deeper menu for path:" + path);
		}

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public void setItemDisplayText(Path path, boolean toloc, String text) {
		if (toloc) {
			text = this.getClient(true).localized(text);
		}
		ItemView iv = this.getOrCreateItem(path, false);
		iv.setDisplayText(false, text);
	}

	/*
	 *Mar 30, 2013
	 */
	@Override
	public void addItemIfNotExist(Path path) {
		// 
		if(null != this.getItem(path)){
			return;
		}
		this.addItem(path);
	}


}
