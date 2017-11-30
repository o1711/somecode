/**
 *  
 */
package com.graphscape.commons.lang;


/**
 * @author wu
 * 
 */
public interface TracableI extends HasIdI, HasPathI {

	public String getParentId();

	public String getRootId();

}
