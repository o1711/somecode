/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.core.core;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.ElementObjectI;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface WidgetI extends ElementObjectI {
	

	public static interface CreaterI<T extends WidgetI> {
		
		public Class<T> getWidgetType();

		public T create(ContainerI c, String name, UiPropertiesI<Object> pts);

	}

	public void setVisible(boolean v);

	public boolean isVisible();

	@Deprecated
	// use ElementWrapper.click()
	public void _click();

}
