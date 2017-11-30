/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider.resolver;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.lang.ResolverI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class FixLongValueResolver implements ResolverI<ByteCursorI, Long> {

	long width;

	public FixLongValueResolver(long width) {
		this.width = width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.ResolverI#resolve(java.lang.Object)
	 */
	@Override
	public Long resolve(ByteCursorI s) {
		return this.width;
	}

}
