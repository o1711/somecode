/**
 * Jun 25, 2012
 */
package com.graphscape.gwt.commons.widget.basic;

import com.graphscape.gwt.commons.widget.BasicI;

/**
 * @author wuzhen
 * 
 */
public interface LabelI extends BasicI {

	/**
	 * @param key
	 */
	public void setText(String key);

	public void setText(String key, boolean loc);

	public void setTitle(String title);

	public void setTextAndTitle(String key, boolean loc, String title);

}
