/**
 * Jan 24, 2014
 */
package com.graphscape.commons.probject.provider.comparators;

import java.util.Comparator;

import com.graphscape.commons.probject.provider.PropertyKey;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class PropertyKeyComparator implements Comparator<PropertyKey> {

	private StringComparator stringComparator = new StringComparator();

	@Override
	public int compare(PropertyKey o1, PropertyKey o2) {
		int comp = this.stringComparator.compare(o1.getId(), o2.getId());
		if (comp != 0) {
			return comp;
		}
		comp = this.stringComparator.compare(o1.getKey(), o2.getKey());//
		return comp;
	}

}
