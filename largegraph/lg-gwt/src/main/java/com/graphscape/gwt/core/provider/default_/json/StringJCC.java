/**
 * Jun 23, 2012
 */
package com.graphscape.gwt.core.provider.default_.json;

import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.graphscape.gwt.core.CodecI;
import com.graphscape.gwt.core.provider.default_.support.JsonCodecCSupport;

/**
 * @author wu
 * 
 */
public class StringJCC extends JsonCodecCSupport<String> implements CodecI<String> {

	/** */
	public StringJCC(FactoryI f) {
		super("s", String.class, f);

	}

	/* */
	@Override
	protected String decodeWithOutType(JSONValue jv) {
		JSONString js = (JSONString) jv;
		String rt = (js.stringValue());
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(String d) {

		return new JSONString(d);

	}

}
