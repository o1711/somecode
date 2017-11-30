/**
 * Jun 23, 2012
 */
package com.graphscape.gwt.core.provider.default_.json;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.graphscape.gwt.core.CodecI;
import com.graphscape.gwt.core.provider.default_.support.JsonCodecCSupport;

/**
 * @author wu
 * 
 */
public class IntegerJCC extends JsonCodecCSupport<Integer> implements CodecI<Integer> {

	/** */
	public IntegerJCC(FactoryI f) {
		super("i", Integer.class, f);
	}

	/* */
	@Override
	protected Integer decodeWithOutType(JSONValue js) {

		int v = Integer.valueOf(js.toString());
		Integer rt = Integer.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(Integer d) {

		return new JSONNumber(d);

	}

}
