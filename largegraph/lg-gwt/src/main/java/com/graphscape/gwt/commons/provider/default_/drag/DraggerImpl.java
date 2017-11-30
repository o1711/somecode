/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 19, 2012
 */
package com.graphscape.gwt.commons.provider.default_.drag;

import com.graphscape.gwt.commons.drag.DragableI;
import com.graphscape.gwt.commons.drag.DraggerI;
import com.graphscape.gwt.commons.provider.default_.drag.DragableWrapper;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.support.UiObjectSupport;

/**
 * @author wuzhen
 * 
 */
public class DraggerImpl extends UiObjectSupport implements DraggerI {

	/**
	 * @param c
	 */
	public DraggerImpl(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addDragable(DragableI db) {
		DragableWrapper di = new DragableWrapper(this.container, db);
		di.parent(this);

	}

}
