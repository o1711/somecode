/**
 *  Jan 30, 2013
 */
package com.graphscape.gwt.commons.frwk;

import com.graphscape.gwt.commons.frwk.ViewReferenceI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wuzhen
 * @deprecated remove this class
 */
public interface ViewReferenceI {

	public static interface AwareI {
		public void setViewReference(ViewReferenceI vr);
	}

	public Path getPath();
	
	public WidgetI getManagedWidget();

	public void select(boolean sel);

	public boolean isSelect();
	

}
