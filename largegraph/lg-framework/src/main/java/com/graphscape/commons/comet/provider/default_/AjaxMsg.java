/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.graphscape.commons.comet.provider.default_;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.util.Path;

/**
 * @author wu
 *         <p>
 *         Note,AjaxMsg's property type only two kind:String and JsonArray for
 *         message.
 */
public class AjaxMsg extends DefaultProperties<Object> {

	public static final Path CONNECT = Path.valueOf("ajax/connect");

	public static final Path CLOSE = Path.valueOf("ajax/close");

	public static final Path MESSAGE = Path.valueOf("ajax/message");

	public static final Path ERROR = Path.valueOf("ajax/error");

	public static final Path HEART_BEAT = Path.valueOf("ajax/heart-beat");

	public static final Path INTERRUPT = Path.valueOf("ajax/interrupt");// FOR
																		// interrupt,see
																		// ajax
																		// msg
																		// context

	public static final String PK_PATH = "_path";

	public static final String PK_CONTENT = "_content";

	public static final String PK_CONNECT_SESSION_ID = "_session_id";

	public static final String PK_ERROR_CODE = "_error_code";

	public static final String PK_ERROR_MSG = "_error_message";

	public static final String ERROR_CODE_SESSION_NOTFOUND = "session-not-found";

	protected static JsonParser parser = new JsonParser();

	protected static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static AjaxMsg valueOf(JsonObject jo) {
		AjaxMsg rt = new AjaxMsg();
		rt.setProperties(jo);
		return rt;
	}

	public String getSessionId() {
		return (String) this.getProperty(PK_CONNECT_SESSION_ID);
	}

	public void setProperties(JsonObject jo) {
		for (Map.Entry<String, JsonElement> e : jo.entrySet()) {
			JsonElement je = e.getValue();
			Object decoded = je;;
			if (je instanceof JsonPrimitive) {
				JsonPrimitive jp = (JsonPrimitive) je;
				decoded = jp.getAsString();
			}
			
			this.setProperty(e.getKey(), decoded);

		}

	}

	private AjaxMsg() {

	}

	public AjaxMsg(Path path) {
		this.setProperty(PK_PATH, path.toString());
	}

	public static AjaxMsg interruptMsg() {
		return new AjaxMsg(AjaxMsg.INTERRUPT);
	}

	public boolean isInterruptMsg() {
		return this.isPath(INTERRUPT);
	}

	public boolean isPath(Path path) {
		return path.equals(this.getPath());
	}

	public Path getPath() {
		String ps = (String) this.getProperty(PK_PATH, true);
		return Path.valueOf(ps);
	}

	/**
	 * May 8, 2013
	 */
	public static List<AjaxMsg> tryParseMsgArray(Reader reader) {
		//
		List<AjaxMsg> rt = new ArrayList<AjaxMsg>();
		JsonArray jsa = (JsonArray) parser.parse(reader);
		for (int i = 0; i < jsa.size(); i++) {

			JsonObject jo = (JsonObject) jsa.get(i);

			JsonPrimitive jp = (JsonPrimitive) jo.get(PK_PATH);
			String pathS = jp.getAsString();

			Path path = Path.valueOf(pathS);
			AjaxMsg am = new AjaxMsg(path);
			am.setProperties(jo);
			rt.add(am);
		}
		return rt;
	}

	/**
	 * @return
	 */
	public JsonObject toJsonObject() {
		JsonObject rt = new JsonObject();
		for (Map.Entry<String, Object> e : this.getAsMap().entrySet()) {
			Object value = e.getValue();
			JsonElement jsV = null;
			if (value instanceof String) {
				jsV = new JsonPrimitive((String) value);
			} else if (value instanceof JsonElement) {
				jsV = (JsonElement) value;
			}
			rt.add(e.getKey(), jsV);

		}
		return rt;
	}

}
