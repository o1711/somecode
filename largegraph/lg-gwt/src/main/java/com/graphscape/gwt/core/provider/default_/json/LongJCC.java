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
public class LongJCC extends JsonCodecCSupport<Long> implements CodecI<Long> {

	/** */
	public LongJCC(FactoryI f) {
		super("l", Long.class, f);
	}

	/* */
	@Override
	protected Long decodeWithOutType(JSONValue js) {
		long v = Long.valueOf(js.toString());
		Long rt = Long.valueOf(v);
		return rt;
	}

	/* */
	@Override
	protected JSONValue encodeWithOutType(Long d) {

		return new JSONNumber(d);

	}

}
