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
public class IntegerJSM extends JsonMarshallerSupport<Integer, JsonPrimitive> implements
		MarshallerI<JsonPrimitive> {

	/** */
	public IntegerJSM(MarshallingProviderI f) {
		super("i", Integer.class, f, false);
	}

	/* */
	@Override
	protected Integer decodeWithOutType(JsonPrimitive js) {

		return js.getAsInt();
	}

	/* */
	@Override
	protected JsonPrimitive encodeWithOutType(Integer d) {

		return new JsonPrimitive(d);

	}

}
