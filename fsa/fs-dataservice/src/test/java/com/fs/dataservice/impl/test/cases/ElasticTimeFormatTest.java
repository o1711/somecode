/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 20, 2013
 */
package com.fs.dataservice.impl.test.cases;

import java.util.Date;

import junit.framework.TestCase;

import com.fs.dataservice.core.impl.ElasticTimeFormat;

/**
 * @author wu
 * 
 */
public class ElasticTimeFormatTest extends TestCase {

	public void test() {
		String s = ElasticTimeFormat.format(new Date());
		System.out.println(s);
		Date d = ElasticTimeFormat.parse("2013-01-20T15:23:40.713Z");
	}
}
