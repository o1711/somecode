package com.graphscape.gwt.whosegraph.data;

import com.graphscape.gwt.core.data.PropertiesData;

/**
 * 
 * @author wuzhen
 * 
 */
public class ModuleData extends PropertiesData<Object> {

	public ModuleData(PropertiesData<Object> data) {
		super();
		this.setProperties(data);
	}
	

	public String getModuleId() {
		return (String) this.getProperty("moduleId", true);
	}

}
