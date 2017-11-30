/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;

/**
 * @author wu
 * 
 */
public abstract class ListJsonMarshallerSupport<T> extends JsonMarshallerSupport<T, JsonArray>
		implements MarshallerI<JsonArray> {

	/** */
	public ListJsonMarshallerSupport(String typeCode, Class<T> cls, MarshallingProviderI f) {
		super(typeCode, cls, f);
	}

	/* */
	@Override
	public T decodeWithOutType(JsonArray jv) {
		JsonArray jo = (JsonArray) jv;
		List rt = new ArrayList();
		for (int i = 0; i < jo.size(); i++) {

			JsonElement jvX = jo.get(i);// must be array for any
													// data
			String type = JsonTypeCode.resolveTypeCode(jvX);
			MarshallerI c = this.provider.getMarshaller(type);

			Object value = c.unmarshal(jvX);
			rt.add(value);
		}

		return this.convert(rt);

	}

	protected abstract T convert(List l);

	protected abstract List convert(T t);

	/* */
	@Override
	public JsonArray encodeWithOutType(T t) {
		List ud = this.convert(t);
		JsonArray rt = new JsonArray();
		for (int i = 0; i < ud.size(); i++) {
			Object da = ud.get(i);
			MarshallerI c = this.provider.getMarshaller(da.getClass());

			JsonArray value = (JsonArray) c.marshal(da);
			rt.add(value);
		}
		return rt;

	}

}
