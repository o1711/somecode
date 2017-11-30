/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.support.SimpleMarshallerSupport;
import com.graphscape.commons.marshal.support.SimpleMarshallingProviderSupport;
import com.graphscape.commons.message.MessageI;

/**
 * @author wu
 * 
 */
public class SimpleStringMarshallingProvider extends SimpleMarshallingProviderSupport<String> {

	private static class JsonStringMarshaller extends SimpleMarshallerSupport {

		protected MarshallerI<JsonElement> target;

		protected Gson gson;

		protected JsonParser jsonParser;

		JsonStringMarshaller(String tc, Class cls, SimpleStringMarshallingProvider provider, MarshallerI<JsonElement> t) {
			super(tc, cls, provider);
			this.target = t;
			this.gson = provider.gson;
			this.jsonParser = new JsonParser();

		}

		@Override
		protected Object decodeInternal(Object js) {

			JsonElement jsv = this.jsonParser.parse((String) js);
			Object rt = this.target.unmarshal(jsv);

			return rt;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.graphscape.commons.marshal.support.SimpleMarshallerSupport#
		 * encodeInternal(java.lang.Object)
		 */
		@Override
		protected Object encodeInternal(Object d) {
			JsonArray jsv = (JsonArray) this.target.marshal(d);

			return this.gson.toJson(jsv);
		}

	}

	SimpleJsonMarshallingProvider target;

	private Gson gson;

	public SimpleStringMarshallingProvider() {
		this(false);
	}

	private SimpleStringMarshallingProvider(boolean prettyPrint) {
		this.target = new SimpleJsonMarshallingProvider();
		GsonBuilder gb = new GsonBuilder();
		if (prettyPrint) {
			gb.setPrettyPrinting();
		}

		this.gson = gb.create();
		//
		this.add("M", MessageI.class);
		this.add("O", PropertiesI.class);
	}

	public void add(String tc, Class cls) {

		MarshallerI target = this.target.getMarshaller(cls, true);
		this.add(new JsonStringMarshaller(tc, cls, this, target));

	}

}
