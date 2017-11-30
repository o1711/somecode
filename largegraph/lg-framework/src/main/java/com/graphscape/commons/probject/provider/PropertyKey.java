/**
 * Jan 24, 2014
 */
package com.graphscape.commons.probject.provider;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class PropertyKey {
	private String id;
	private String key;

	public PropertyKey(String id, String key) {
		this.id = id;
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public String getKey() {
		return key;
	}
}
