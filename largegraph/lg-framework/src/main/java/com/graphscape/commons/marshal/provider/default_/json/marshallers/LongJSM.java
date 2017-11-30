/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json.marshallers;

import com.google.gson.JsonPrimitive;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.json.JsonMarshallerSupport;

/**
 * @author wu
 * 
 */
public class LongJSM extends JsonMarshallerSupport<Long, JsonPrimitive> implements MarshallerI<JsonPrimitive> {

	/** */
	public LongJSM(MarshallingProviderI f) {
		super("l", Long.class, f);
	}

	/* */
	@Override
	protected Long decodeWithOutType(JsonPrimitive js) {

		return js.getAsLong();
	}

	/* */
	@Override
	protected JsonPrimitive encodeWithOutType(Long d) {

		return new JsonPrimitive(d);

	}

}
