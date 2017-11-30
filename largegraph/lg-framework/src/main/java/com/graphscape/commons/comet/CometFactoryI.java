/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.graphscape.commons.comet;


/**
 * @author wu
 * 
 */
public interface CometFactoryI {

	public CometManagerI addManager( String protocol, String name);

	public CometManagerI getManager(String protocol, String name, boolean force);

}
