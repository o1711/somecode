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
public class BooleanJSM extends JsonMarshallerSupport<Boolean, JsonPrimitive> implements
		MarshallerI<JsonPrimitive> {

	/** */
	public BooleanJSM(MarshallingProviderI f) {
		super("b", Boolean.class, f, false);
	}

	/* */
	@Override
	protected Boolean decodeWithOutType(JsonPrimitive js) {

		return js.getAsBoolean();
	}

	/* */
	@Override
	protected JsonPrimitive encodeWithOutType(Boolean d) {

		return new JsonPrimitive(d);

	}

}
