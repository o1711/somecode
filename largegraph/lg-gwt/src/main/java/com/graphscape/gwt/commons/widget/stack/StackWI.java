/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.graphscape.gwt.commons.widget.stack;

import com.graphscape.gwt.commons.widget.stack.StackItemI;
import com.graphscape.gwt.core.CompositeI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface StackWI extends CompositeI {

	public StackItemI getDefaultItem(boolean force);

	public StackItemI getSelected(boolean force);

	public StackItemI insert(Path path, WidgetI child, boolean select);

	public StackItemI getByPath(Path p, boolean force);

	public void remove(Path path);

	public int getSize();

	public void updateSelect(StackItemI im);

}
