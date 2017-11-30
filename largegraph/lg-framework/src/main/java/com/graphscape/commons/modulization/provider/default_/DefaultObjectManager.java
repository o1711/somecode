/**
 * Dec 8, 2013
 */
package com.graphscape.commons.modulization.provider.default_;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.lang.LifeCycleI;
import com.graphscape.commons.modulization.ModuleI;
import com.graphscape.commons.modulization.ObjectManagerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultObjectManager {

	protected ModuleI module;

	protected Map<String, ObjectManagerI> omanagerMap = new HashMap<String, ObjectManagerI>();

	public DefaultObjectManager(ModuleI module) {
		this.module = module;
		this.omanagerMap.put("container", new ContainerObjectManager(module.getContainer()));

	}

	public void manage(String mgr, Object obj) {
		
		if (mgr == null) {//not manage
			return;
		}

		ObjectManagerI om = this.getObjectManager(mgr);
		om.manage(obj);
		// TODO configurable
		if (obj instanceof LifeCycleI) {
			LifeCycleI lc = (LifeCycleI) obj;
			lc.start();// NOTE?
		}
	}

	protected ObjectManagerI getObjectManager(String type) {
		return this.omanagerMap.get(type);
	}

}
