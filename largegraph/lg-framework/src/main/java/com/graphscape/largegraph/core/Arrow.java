/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core;

import com.graphscape.commons.lang.Enumeration;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class Arrow extends Enumeration {
	public static final Arrow HEAD = Arrow.valueOf("HEAD");
	public static final Arrow TAIL = Arrow.valueOf("TAIL");
	public static final Arrow BOTH = Arrow.valueOf("BOTH");

	/**
	 * @param name
	 */
	private Arrow(String name) {
		super(name);
	}

	public static Arrow valueOf(String name) {
		return new Arrow(name);
	}

}
