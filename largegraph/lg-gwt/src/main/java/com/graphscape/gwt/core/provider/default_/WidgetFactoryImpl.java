/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.core.provider.default_;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.WidgetFactoryI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.core.WidgetI.CreaterI;
import com.graphscape.gwt.core.support.MapProperties;

/**
 * @author wuzhen
 * 
 */
public class WidgetFactoryImpl implements WidgetFactoryI {
	/*
	
	 */
	private Map<Class, WidgetI.CreaterI> createrMap;

	private ContainerI container;

	public WidgetFactoryImpl(ContainerI c) {
		this.container = c;
		this.createrMap = new HashMap<Class, WidgetI.CreaterI>();
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls) {
		return create(cls, null);
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls, String name) {
		return this.create(cls, name, new Object[] {});
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls, String name, Object... pts) {

		UiPropertiesI<Object> pts2 = MapProperties.valueOf(pts);

		return this.create(cls, name, pts2);

	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls, String name, UiPropertiesI<Object> pts) {
		WidgetI.CreaterI<T> wic = this.createrMap.get(cls);
		if (wic == null) {
			throw new UiException("no creater found for widget type:" + cls);
		}

		T rt = wic.create(this.container, name, pts);
		return rt;
	}

	/* */
	@Override
	public void addCreater(CreaterI wic) {
		this.createrMap.put(wic.getWidgetType(), wic);
	}

}
