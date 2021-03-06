/**
 * Jun 23, 2012
 */
package com.graphscape.gwt.core.provider.default_.support;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.graphscape.gwt.core.CodecI;

/**
 * @author wu
 * 
 */
public abstract class ListJCCSupport<T> extends JsonCodecCSupport<T> implements CodecI<T> {

	/** */
	public ListJCCSupport(String type, Class<T> cls, FactoryI f) {
		super(type, cls, f);
	}

	/* */
	@Override
	public T decodeWithOutType(JSONValue jv) {
		JSONArray jo = (JSONArray) jv;
		List rt = new ArrayList();
		for (int i = 0; i < jo.size(); i++) {
			JSONArray jvX = (JSONArray) jo.get(i);// must be array for any
													// data
			String type = this.getType(jvX);
			CodecI c = this.factory.getCodec(type);

			Object value = c.decode(jvX);

			rt.add(value);

		}

		return convert(rt);

	}

	protected abstract T convert(List l);

	protected abstract List convert(T t);

	/* */
	@Override
	public JSONValue encodeWithOutType(T t) {
		List ud = this.convert(t);
		JSONArray rt = new JSONArray();

		for (int i = 0; i < ud.size(); i++) {
			Object da = ud.get(i);
			CodecI c = this.factory.getCodec(da.getClass());

			JSONArray value = (JSONArray) c.encode(da);
			rt.set(i, value);
		}
		return rt;

	}

}
