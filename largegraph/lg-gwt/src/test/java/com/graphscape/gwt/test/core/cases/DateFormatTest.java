/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 29, 2012
 */
package com.graphscape.gwt.test.core.cases;

import com.graphscape.gwt.core.data.basic.DateData;
import com.graphscape.gwt.core.util.DateUtil;
import com.graphscape.gwt.test.core.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class DateFormatTest extends TestBase {

	public void testDate() {
		String dateS = "2012-11-29T11:13:02.969+0800";
		DateData date = DateUtil.parse(dateS);

	}
}
