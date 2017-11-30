/**
 * Dec 15, 2013
 */
package com.graphscape.commons.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CollectionUtil {

	public static <K, V> Map<V, K> reverse(Map<K, V> map) {

		Map<V, K> rt = new HashMap<V, K>();
		for (Map.Entry<K, V> e : map.entrySet()) {
			rt.put(e.getValue(), e.getKey());
		}
		return rt;
	}

}
