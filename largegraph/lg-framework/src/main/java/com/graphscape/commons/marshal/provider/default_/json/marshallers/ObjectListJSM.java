/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json.marshallers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.json.JsonMarshallerSupport;
import com.graphscape.commons.marshal.provider.default_.json.JsonTypeCode;

/**
 * @author wu
 * 
 */
public class ObjectListJSM extends JsonMarshallerSupport<List, JsonArray> implements
		MarshallerI<JsonArray> {

	/** */
	public ObjectListJSM(MarshallingProviderI f) {
		super("L", List.class, f);
	}

	/* */
	@Override
	public List decodeWithOutType(JsonArray jo) {
		List rt = new ArrayList();
		for (int i = 0; i < jo.size(); i++) {

			JsonElement jvX = jo.get(i);// must be array for any
													// data
			String type = JsonTypeCode.resolveTypeCode(jvX);
			MarshallerI c = this.provider.getMarshaller(type);

			Object value = c.unmarshal(jvX);
			rt.add(value);
		}

		return rt;

	}

	/* */
	@Override
	public JsonArray encodeWithOutType(List ud) {
		JsonArray rt = new JsonArray();
		for (int i = 0; i < ud.size(); i++) {
			Object da = ud.get(i);
			MarshallerI c = this.provider.getMarshaller(da.getClass());

			JsonElement value = (JsonElement) c.marshal(da);
			rt.add(value);
		}
		return rt;

	}

}
