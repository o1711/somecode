/**
 *  Feb 6, 2013
 */
package com.graphscape.gwt.commons.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuzhen
 * 
 */
public class ListAndMap<K, T> {
	private List<T> list = new ArrayList<T>();
	private Map<K, T> map = new HashMap<K, T>();

	public void add(K k, T t) {
		this.list.add(t);

	}
}
