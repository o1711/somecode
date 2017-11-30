package com.graphscape.gwt.whosegraph.provider.default_;

import com.graphscape.gwt.commons.CreaterI;
import com.graphscape.gwt.commons.mvc.support.ControlSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.whosegraph.MainAppConstants;
import com.graphscape.gwt.whosegraph.MainControlI;
import com.graphscape.gwt.whosegraph.provider.default_.view.ModuleListViewImpl;
import com.graphscape.gwt.whosegraph.view.ModuleListViewI;

/**
 * 
 * @author wuzhen
 *
 */
public class MainControlImpl extends ControlSupport implements MainControlI{

	/**
	 * @param c
	 * @param name
	 */
	public MainControlImpl(ContainerI c, String name) {
		super(c, name);
	}

	
	@Override
	public ModuleListViewI openModuleListView() {
		ModuleListViewImpl rt = this.getOrCreateViewInBody(MainAppConstants.VP_MODULE_LIST, new CreaterI<ModuleListViewImpl>() {

			@Override
			public ModuleListViewImpl create(ContainerI ct) {
				return new ModuleListViewImpl(ct);
			}
		}, true);
		return rt;
	}

}
