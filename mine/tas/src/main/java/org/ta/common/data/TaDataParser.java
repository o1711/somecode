package org.ta.common.data;

import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ta.common.config.TaException;

public class TaDataParser {
	JSONParser parser = new JSONParser();

	public TaData parseJson(Reader reader) {
		try {
			JSONObject obj = (JSONObject) parser.parse(reader);

			TaData rt = this.process(obj);
			return rt;

		} catch (IOException | ParseException e) {
			throw new TaException(e);
		}

	}

	private TaData process(Object value) {
		if (value instanceof String) {
			return new TaStringData((String) value);
		} else if (value instanceof JSONObject) {
			TaObjectData rt = new TaObjectData();
			JSONObject obj = (JSONObject) value;
			for (Object keyS : obj.keySet()) {
				String keyI = (String) keyS;
				Object valueI = obj.get(keyI);
				TaData dataI = this.process(valueI);
				rt.set(keyI, dataI);
			}
			return rt;
		} else if (value instanceof JSONArray) {
			TaArrayData rt = new TaArrayData();
			JSONArray arr = (JSONArray) value;
			for (int i = 0; i < arr.size(); i++) {
				Object valueI = arr.get(i);
				TaData dataI = this.process(valueI);
				rt.add(dataI);
			}

			return rt;
		} else {
			return null;
		}
	}

}
