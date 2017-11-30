/**
 * 
 */
package com.graphscape.commons.lang;

/**
 * @author wuzhen
 * 
 */
public class Enumeration {

	protected String name;

	public String getName() {
		return name;
	}

	public Enumeration(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Enumeration)) {
			return false;
		}
		return name.equals(((Enumeration) obj).name);
	}

	@Override
	public String toString() {
		return this.name.toString();
	}

}
