/**
 * Jan 9, 2014
 */
package com.graphscape.commons.record.provider.comparators;

import java.util.Comparator;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class IntComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {

		return (o1 - o2);
	}

}
