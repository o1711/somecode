/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package com.fs.commons.impl.session;

import com.fs.commons.api.support.SessionSupport;

/**
 * @author wu
 * 
 */
public class SessionImpl extends SessionSupport {

	/**
	 * @param group
	 */
	public SessionImpl(String group, long timeout) {
		super(group, timeout);
	}

	public SessionImpl(String group, String id, long timeout) {
		super(group, id, timeout);
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void destroy() {
		//

	}

}
