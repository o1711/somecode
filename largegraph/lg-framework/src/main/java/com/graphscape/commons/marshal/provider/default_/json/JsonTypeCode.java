package com.graphscape.commons.marshal.provider.default_.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.graphscape.commons.lang.GsException;

public class JsonTypeCode {
	public static final String BOOL = "b";
	public static final String STRING = "s";
	public static final String DATE = "d";
	public static final String ERROR = "E";
	public static final String ERRORS = "ES";
	public static final String INT = "i";
	public static final String LONG = "l";
	public static final String LIST = "L";
	public static final String OBJECT = "O";
	public static final String MSG = "M";

	public static String resolveTypeCode(JsonElement js) {

		if (js instanceof JsonArray) {//

			JsonElement jt = ((JsonArray) js).get(0);// type code is here.

			JsonPrimitive jp = (JsonPrimitive) jt;
			if (!jp.isString()) {
				throw new GsException("not a string");
			}
			String rt = jp.getAsString();
			return rt;
		}
		// use the default type by value,mapping.
		if (js instanceof JsonObject) {
			return OBJECT;
		}

		if (js instanceof JsonPrimitive) {
			JsonPrimitive jsp = (JsonPrimitive) js;
			if (jsp.isString()) {
				return STRING;
			}
			if (jsp.isNumber()) {
				return INT;// int?
			}

			if (jsp.isBoolean()) {
				return BOOL;
			}

		}
		throw new GsException("cannot resolve the type for json element:" + js);

	}
}
