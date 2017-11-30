/**
 * Jun 23, 2012
 */
package com.graphscape.gwt.core.provider.default_.json;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.graphscape.gwt.core.CodecI;
import com.graphscape.gwt.core.data.basic.DateData;
import com.graphscape.gwt.core.provider.default_.support.JsonCodecCSupport;
import com.graphscape.gwt.core.util.DateUtil;

/**
 * @author wu
 * 
 */
public class DateJCC extends JsonCodecCSupport<DateData> implements CodecI<DateData> {

	/** */
	public DateJCC(FactoryI f) {
		super("d", DateData.class, f);
	}

	/* */
	@Override
	protected DateData decodeWithOutType(JSONValue js) {
		if (js instanceof JSONNull) {
			return null;//
		}
		JSONString jsS = (JSONString) js;
		DateData rt = DateUtil.parse(jsS.stringValue());
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(DateData d) {

		String ds = DateUtil.format(d, false);
		return ds == null ? JSONNull.getInstance() : new JSONString(ds);

	}

}
