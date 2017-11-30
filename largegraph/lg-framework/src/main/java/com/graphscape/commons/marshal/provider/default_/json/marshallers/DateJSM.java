/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json.marshallers;

import java.util.Date;

import com.google.gson.JsonPrimitive;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.json.JsonMarshallerSupport;
import com.graphscape.commons.util.DateUtil;

/**
 * @author wu
 * 
 */
public class DateJSM extends JsonMarshallerSupport<Date, JsonPrimitive> implements MarshallerI<JsonPrimitive> {

	/** */
	public DateJSM(MarshallingProviderI f) {
		super("d", Date.class, f);
	}

	/* */
	@Override
	protected Date decodeWithOutType(JsonPrimitive js) {

		return DateUtil.parse(js.getAsString());
	}

	/* */
	@Override
	protected JsonPrimitive encodeWithOutType(Date d) {

		return new JsonPrimitive(DateUtil.format(d));

	}

}
