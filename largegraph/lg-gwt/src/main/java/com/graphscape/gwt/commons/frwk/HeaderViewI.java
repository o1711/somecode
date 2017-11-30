/**
 *  Jan 31, 2013
 */
package com.graphscape.gwt.commons.frwk;

import com.graphscape.gwt.commons.mvc.ViewI;
import com.graphscape.gwt.core.commons.Path;

/**
 * @author wuzhen
 * 
 */
public interface HeaderViewI extends ViewI {

	public void addItem(Path path);
	
	public void addItem(Path path, boolean left);
	
	public void addItemIfNotExist(Path path);

	public void setItemDisplayText(Path path,boolean toloc, String text);

	public void tryRemoveItem(Path path);

}
