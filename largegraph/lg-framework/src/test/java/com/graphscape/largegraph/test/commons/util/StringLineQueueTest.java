/**
 * Dec 21, 2013
 */
package com.graphscape.largegraph.test.commons.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.util.StringLineQueue;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class StringLineQueueTest extends TestCase {

	public void testStringLineQueue() {
		StringReader sr = new StringReader("line1\nline2\nline3");
		StringLineQueue slq = new StringLineQueue(sr, '\n');
		FutureI<Object> f = slq.submit();
		List<String> ls = new ArrayList<String>();
		while (true) {
			String l = slq.readLine();

			if (l == null) {
				break;
			}
			ls.add(l);
		}
		Assert.assertEquals(3, ls.size());
		Assert.assertEquals("line1",ls.get(0));
		Assert.assertEquals("line2",ls.get(1));
		Assert.assertEquals("line3",ls.get(2));
		
	}
}
