/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json.marshallers;

import com.google.gson.JsonPrimitive;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.provider.default_.json.JsonMarshallerSupport;

/**
 * @author wu
 * 
 */
public class StringJSM extends JsonMarshallerSupport<String, JsonPrimitive> implements MarshallerI<JsonPrimitive> {

	/** */
	public StringJSM(MarshallingProviderI f) {
		super("s", String.class, f, false);

	}

	/* */
	@Override
	protected String decodeWithOutType(JsonPrimitive jv) {
		return jv.getAsString();
	}

	/* */
	@Override
	protected JsonPrimitive encodeWithOutType(String d) {

		return new JsonPrimitive(d);

	}

}
