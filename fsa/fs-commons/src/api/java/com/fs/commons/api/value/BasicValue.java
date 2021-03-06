/**
 * Jun 23, 2012
 */
package com.fs.commons.api.value;

import com.fs.commons.api.lang.ObjectUtil;

/**
 * @author wu
 * 
 */
public class BasicValue<T> implements ValueI {
	protected T value;

	protected BasicValue(T t) {
		this.value = t;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	protected boolean isEquals(BasicValue<T> bd) {
		return ObjectUtil.nullSafeEquals(this.value, bd.value);
	}
}
