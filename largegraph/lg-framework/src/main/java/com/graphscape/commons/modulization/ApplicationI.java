/**
 * 
 */
package com.graphscape.commons.modulization;

import com.graphscape.commons.lang.EnvironmentAwareI;
import com.graphscape.commons.lang.LifeCycleI;

/**
 * @author wuzhen
 * 
 */
public interface ApplicationI extends LifeCycleI, EnvironmentAwareI {

	public ModuleI getRootModule();

	public String getId();
	
	public ModuleI getModule(String id,boolean force);

}
