/**
 * Jan 24, 2014
 */
package com.graphscape.commons.probject.provider.comparators;

import java.util.Comparator;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class StringComparator implements Comparator<String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(String o1, String o2) {
		if (o1 == null && o2 == null) {
			return 0;
		}
		if (o1 == null && o2 != null) {
			return -1;
		}
		if (o1 != null && o2 == null) {
			return 1;
		}

		int len1 = o1.length();
		int len2 = o2.length();
		int lim = Math.min(len1, len2);

		int k = 0;
		while (k < lim) {
			char c1 = o1.charAt(k);
			char c2 = o2.charAt(k);
			if (c1 != c2) {
				return c1 - c2;
			}
			k++;
		}
		return len1 - len2;
	}

}
