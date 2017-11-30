/**
 * Jul 20, 2012
 */
package com.graphscape.gwt.core.data.basic;

import java.util.Date;

import com.graphscape.gwt.core.data.BasicData;
import com.graphscape.gwt.core.data.basic.DateData;
import com.graphscape.gwt.core.util.DateUtil;

/**
 * @author wu
 * 
 */
public class DateData extends BasicData<Long> {

	/** */
	protected DateData(Long t) {
		super(t);
	}

	public static DateData valueOf(Date date) {
		return date == null ? null : valueOf(date.getTime());
	}

	public static DateData valueOf(Long v) {

		return v == null ? null : new DateData(v);
	}

	public String getFormated() {
		return DateUtil.format(this);
	}
}
