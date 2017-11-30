/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.core;

import com.graphscape.gwt.core.WidgetFactoryI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface WidgetFactoryI {

	public static interface AwareI {
		public void setWidgetFactory(WidgetFactoryI wf);
	}
	
	public <T extends WidgetI> T create(Class<T> cls);
	
	public <T extends WidgetI> T create(Class<T> cls, String name);
	
	public <T extends WidgetI> T create(Class<T> cls, String name, Object...pts);

	public <T extends WidgetI> T create(Class<T> cls, String name, UiPropertiesI<Object> pts);

	public <T extends WidgetI> void addCreater(WidgetI.CreaterI<T> wic);

}
