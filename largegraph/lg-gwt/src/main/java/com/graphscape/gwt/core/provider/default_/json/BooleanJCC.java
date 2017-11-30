/**
 * Jun 23, 2012
 */
package com.graphscape.gwt.core.provider.default_.json;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONValue;
import com.graphscape.gwt.core.CodecI;
import com.graphscape.gwt.core.provider.default_.support.JsonCodecCSupport;

/**
 * @author wu
 * 
 */
public class BooleanJCC extends JsonCodecCSupport<Boolean> implements CodecI<Boolean> {

	/** */
	public BooleanJCC(FactoryI f) {
		super("b", Boolean.class, f);
	}

	/* */
	@Override
	protected Boolean decodeWithOutType(JSONValue js) {

		Boolean v = ((JSONBoolean) js).booleanValue();
		Boolean rt = (v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(Boolean d) {

		return JSONBoolean.getInstance(d);

	}

}
