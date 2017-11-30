/**
 *  
 */
package com.graphscape.commons.container;

/**
 * @author wu
 * 
 */
public interface ContainerAwareI {

	void setContainer(ContainerI c);
	
	ContainerI getContainer();

}
