/**
 * 
 */
package com.graphscape.commons.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author wuzhen
 * 
 */
public class JsonUtil {
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private JsonParser jp = new JsonParser();

	public String prettyPrint(String jsonStr) {
		JsonElement json = jp.parse(jsonStr);
		return prettyPrint(json);
	}

	public String prettyPrint(JsonElement json) {
		return gson.toJson(json);

	}
}
