/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.support.SimpleMarshallerSupport;

/**
 * @author wu
 *         <p>
 *         com.google.gson.JsonObject, com.google.gson.JsonArray,
 *         java.lang.String, java.lang.Number, java.lang.Boolean, null
 */
public abstract class JsonMarshallerSupport<T, JV extends JsonElement> extends
		SimpleMarshallerSupport<T, JV> {

	protected boolean encodeTypeCode = true;

	/** */
	public JsonMarshallerSupport(String tc, Class<T> cls, MarshallingProviderI f) {
		this(tc, cls, f, true);
	}

	public JsonMarshallerSupport(String tc, Class<T> cls, MarshallingProviderI f, boolean encodeTypeCode) {
		super(tc, cls, f);
		this.encodeTypeCode = encodeTypeCode;
	}

	/* */
	@Override
	protected T decodeInternal(JV js) {
		JV jv = null;
		if (js instanceof JsonArray) {
			jv = (JV) ((JsonArray) js).get(1);
		} else {
			jv = (JV) js;// type code is not specified, the value is directly
							// presented.
		}

		return this.decodeWithOutType(jv);

	}

	/* */
	@Override
	protected JV encodeInternal(T d) {
		JV jv = this.encodeWithOutType(d);
		if (this.encodeTypeCode) {
			JsonArray rt = new JsonArray();
			rt.add(new JsonPrimitive(this.typeCode));
			rt.add(jv);
			return (JV)rt;
		} else {
			return jv;
		}

	}

	protected abstract T decodeWithOutType(JV jv);

	protected abstract JV encodeWithOutType(T t);

}
