/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core;

import com.graphscape.commons.lang.Enumeration;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class Label extends Enumeration {

	/**
	 * @param name
	 */
	private Label(String name) {
		super(name);
	}

	public static Label valueOf(String name) {
		return new Label(name);
	}

}
