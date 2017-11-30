/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.graphscape.commons.session;

import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.TimeAndUnit;


/**
 * @author wu
 * 
 */
public interface SessionManagerI {

	public Path getPath();

	public SessionI getSession(String id);

	public SessionI touchSession(String id);

	public SessionI createSession(TimeAndUnit timeout);

	public SessionI createSession(String id, TimeAndUnit timeout);
	
	public SessionManagerI createManager(String name);

	public SessionManagerI getManager(String name);
	
/*
	public void addCreatedHandler(HandlerI<SessionI> hdl);

	public void addTimeoutHandler(HandlerI<SessionI> hdl);

	public void addTouchedHandler(HandlerI<SessionI> hdl);
*/
}
