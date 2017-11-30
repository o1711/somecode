/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package com.graphscape.gwt.commons.frwk.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class FormModel extends ModelSupport {

	public static final Location L_ACTION_LIST = Location.valueOf("actionList");

	private List<FieldModel> fieldList;
	/**
	 * @param name
	 */
	public FormModel(String name) {
		super(name);
		this.fieldList = new ArrayList<FieldModel>();
	}

	public List<FieldModel> getFieldModelList() {
		return this.fieldList;
	}


	public <T> T getFieldValue(String fname, T def) {
		FieldModel fm = this.getFieldModel(fname, false);

		T rt = (T) fm.getFieldValue();

		return rt == null ? def : rt;
	}

	public FieldModel getFieldModel(String name, boolean force) {

		List<FieldModel> fmL = this.fieldList;
		for (FieldModel fm : fmL) {
			if (fm.getName().equals(name)) {
				return fm;
			}
		}
		if (force) {
			throw new UiException("force:" + name + ",in:" + this);
		}
		return null;
	}

	public void addAction(Path... names) {
		List<Path> actionList = this.getActionList();
		actionList.addAll(Arrays.asList(names));
		this.setValue(L_ACTION_LIST, actionList);

	}

	public List<Path> getActionList() {
		List<Path> rt = (List<Path>) this.getValue(L_ACTION_LIST);
		return rt == null ? new ArrayList() : rt;
	}

	/**
	 *Feb 23, 2013
	 */
	public void addField(FieldModel rt) {
		this.fieldList.add(rt);
	}

}
