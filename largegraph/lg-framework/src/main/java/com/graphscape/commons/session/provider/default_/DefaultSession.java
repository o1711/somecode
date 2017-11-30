/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.graphscape.commons.session.provider.default_;

import com.graphscape.commons.session.SessionSupport;
import com.graphscape.commons.util.Path;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * @author wu
 * 
 */
public class DefaultSession extends SessionSupport {

	/**
	 * @param path
	 */
	public DefaultSession(Path managerPath, TimeAndUnit timeout) {
		super(managerPath, timeout);
	}

	public DefaultSession(Path managerPath, String id, TimeAndUnit timeout) {
		super(managerPath, id, timeout);
	}

	@Override
	public void destroy() {
		//

	}

}
