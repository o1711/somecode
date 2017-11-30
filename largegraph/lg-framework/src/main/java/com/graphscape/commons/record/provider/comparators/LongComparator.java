/**
 * Jan 9, 2014
 */
package com.graphscape.commons.record.provider.comparators;

import java.util.Comparator;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LongComparator implements Comparator<Long> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Long o1, Long o2) {

		return (int) (o1 - o2);
	}

}
