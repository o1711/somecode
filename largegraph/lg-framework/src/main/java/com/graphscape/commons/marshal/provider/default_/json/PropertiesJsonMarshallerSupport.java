/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;

/**
 * @author wu
 * 
 */
public abstract class PropertiesJsonMarshallerSupport<T> extends JsonMarshallerSupport<T, JsonElement>
		implements MarshallerI<JsonElement> {

	/** */
	public PropertiesJsonMarshallerSupport(String typeCode, Class<T> cls, MarshallingProviderI f) {
		this(typeCode, cls, f, true);
	}

	public PropertiesJsonMarshallerSupport(String typeCode, Class<T> cls, MarshallingProviderI f,
			boolean encodeTypeCode) {
		super(typeCode, cls, f, encodeTypeCode);
	}

	/* */
	@Override
	public T decodeWithOutType(JsonElement je) {
		JsonObject jo = (JsonObject)je;
		PropertiesI<Object> rt = new DefaultProperties<Object>();

		for (Map.Entry<String, JsonElement> e : jo.entrySet()) {
			String key = e.getKey();
			JsonElement jvX = e.getValue();// must be array for any

			Object value = null;
			// data
			if (jvX != null) {

				String type = JsonTypeCode.resolveTypeCode(jvX);
				MarshallerI c = this.provider.getMarshaller(type);

				value = c.unmarshal(jvX);
			}
			rt.setProperty(key, value);
		}

		return this.convert(rt);

	}

	protected abstract T convert(PropertiesI<Object> pts);

	protected abstract PropertiesI<Object> convert(T t);

	/* */
	@Override
	public JsonObject encodeWithOutType(T ud) {
		PropertiesI<Object> pts = this.convert(ud);
		JsonObject rt = new JsonObject();
		List<String> kL = pts.keyList();

		for (String key : kL) {
			Object da = pts.getProperty(key);// TODO
			JsonElement value = null;
			if (da != null) {
				MarshallerI c = this.provider.getMarshaller(da.getClass());
				value = (JsonElement)c.marshal(da);
			}
			rt.add(key, value);

		}
		return rt;

	}

}
