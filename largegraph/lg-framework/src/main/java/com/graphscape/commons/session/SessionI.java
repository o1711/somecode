/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.graphscape.commons.session;

import com.graphscape.commons.lang.HasAttributeI;
import com.graphscape.commons.util.Path;

/**
 * @author wu
 * 
 */
public interface SessionI extends HasAttributeI {

	public Path getPath();

	public String getId();

	public long getIdleTimeoutMs();

	public void destroy();

	public long getCreated();

	public long getTouched();

	public void touch();

	public boolean isTimeout();

}
