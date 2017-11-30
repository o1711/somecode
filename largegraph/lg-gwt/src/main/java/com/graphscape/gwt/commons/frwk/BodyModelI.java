/**
 *  Jan 30, 2013
 */
package com.graphscape.gwt.commons.frwk;

import com.graphscape.gwt.commons.frwk.ViewReferenceI;
import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wuzhen
 * @deprecated
 */
public interface BodyModelI extends ModelI {

	public ViewReferenceI manage(Path p, WidgetI w);

	public ViewReferenceI getManaged(Path p);

	public void showManaged(Path p);

}
