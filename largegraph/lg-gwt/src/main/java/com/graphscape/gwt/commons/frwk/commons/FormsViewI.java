/**
 *  Feb 1, 2013
 */
package com.graphscape.gwt.commons.frwk.commons;

import com.graphscape.gwt.commons.mvc.ViewI;

/**
 * @author wuzhen
 * 
 */
public interface FormsViewI extends ViewI {

	public FormViewI addForm(String name);

	public FormViewI getForm(String name);

	public FormViewI getDefaultForm();
}
