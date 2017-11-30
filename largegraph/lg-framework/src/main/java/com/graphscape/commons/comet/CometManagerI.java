/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 11, 2012
 */
package com.graphscape.commons.comet;

import java.util.List;


/**
 * @author wu
 * 
 */
public interface CometManagerI {

	public List<CometI> getSocketList();
	
	public CometI getSocket(String id);

	public CometI getSocket(String id, boolean b);
	
}
