/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.graphscape.gwt.whosegraph.provider.default_.handler.message;

import java.util.List;

import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;
import com.graphscape.gwt.core.event.EndpointMessageEvent;
import com.graphscape.gwt.whosegraph.data.ModuleData;
import com.graphscape.gwt.whosegraph.provider.default_.handler.support.MHSupport;
import com.graphscape.gwt.whosegraph.view.ModuleListViewI;

/**
 * @author wu
 * 
 */
public class ModuleListRefreshMH extends MHSupport {

	/**
	 * @param c
	 */
	public ModuleListRefreshMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();
		List<ObjectPropertiesData> ld = (List<ObjectPropertiesData>) res.getPayloads().getProperty(
				"moduleList");
		
		ModuleListViewI view = this.getMainControl().openModuleListView();
		
		for (int i = 0; i < ld.size(); i++) {
			ObjectPropertiesData oi = ld.get(i);
			
			ModuleData uem = new ModuleData(oi);
			view.addModule(uem);
		}
	}

}
