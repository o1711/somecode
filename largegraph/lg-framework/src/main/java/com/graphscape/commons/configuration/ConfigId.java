/**
 * 2013Äê12ÔÂ6ÈÕ
 */
package com.graphscape.commons.configuration;

import com.graphscape.commons.util.ObjectUtil;

/**
 * @author wuzhen0808@gmail.com
 *
 */
public class ConfigId {

	protected ConfigId parent;

	protected String name;

	protected int index;

	public ConfigId(ConfigId prt, String name, int idx) {
		this.parent = prt;
		this.name = name;
		this.index = idx;
	}

	public ConfigId getChild(String name, int idx) {
		return new ConfigId(this, name, idx);
	}

	public ConfigId getChild(String name) {
		return this.getChild(name, 0);
	}

	public static ConfigId parse(String value) {
		int idx_dot = value.lastIndexOf(".");
		ConfigId prt = null;
		if (idx_dot >= 0) {//
			String parentValue = value.substring(0, idx_dot);
			prt = ConfigId.parse(parentValue);
			value = value.substring(idx_dot + 1);
		}
		boolean hasIndex = value.endsWith("]");
		int index = 0;
		String name = value;
		if (hasIndex) {

			int idx_1 = value.indexOf("[");
			name = value.substring(0, idx_1);
			String indexS = value.substring(idx_1 + 1, value.length() - 1);

			index = Integer.parseInt(indexS);
		}

		return new ConfigId(prt, name, index);
	}

	@Override
	public int hashCode() {
		int pcode = 0;
		if (this.parent != null) {
			pcode = this.parent.hashCode();
		}
		return pcode * 100 + this.name.hashCode() + this.index;

	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ConfigId)) {
			return false;
		}
		ConfigId o2 = (ConfigId) obj;
		return ObjectUtil.nullSafeEquals(this.parent, o2.parent)
				&& ObjectUtil.nullSafeEquals(this.name, o2.name) && this.index == o2.index;
	}

	public String toString(char sep) {
		return (this.parent == null ? "" : this.parent.toString(sep) + sep) + this.name
				+ (this.index == 0 ? "" : "[" + this.index + "]");
	}
	
	@Override
	public String toString(){
		return toString('.');
	}
	/**
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public int getIndex(){
		return this.index;
	}

	/**
	 * @param configId
	 * @param name2
	 * @param i
	 * @return
	 */
	public static ConfigId valueOf(ConfigId p, String name, int idx) {
		return new ConfigId(p, name, idx);
	}

	/**
	 * @return
	 */
	public ConfigId getParent() {
		return this.parent;
	}
}
