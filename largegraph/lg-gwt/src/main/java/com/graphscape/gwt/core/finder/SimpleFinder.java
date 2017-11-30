/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 14, 2012
 */
package com.graphscape.gwt.core.finder;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.reflect.InstanceOf;
import com.graphscape.gwt.core.support.UiObjectFinderSupport;

/**
 * @author wu
 * 
 */
public class SimpleFinder<T> extends UiObjectFinderSupport<T> {

	protected Class<? extends T> clazz;

	protected Path path;

	@Override
	public List<T> findList() {
		List<T> rt = new ArrayList<T>();

		this.find(this.startPoint, rt);

		return rt;
	}

	protected void find(UiObjectI obj, List<T> result) {
		if (this.isMatch(obj)) {
			result.add((T) obj);
		}
		List<UiObjectI> ol = obj.getChildList(UiObjectI.class);
		for (UiObjectI c : ol) {
			find(c, result);
		}
	}

	protected boolean isMatch(UiObjectI obj) {
		if (this.clazz != null && !InstanceOf.isInstance(this.clazz, obj)) {
			return false;
		}
		if (this.path != null && !obj.getPath().equals(this.path)) {
			return false;
		}

		return true;
	}

}
