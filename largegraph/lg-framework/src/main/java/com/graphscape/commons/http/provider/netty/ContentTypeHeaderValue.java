/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http.provider.netty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ContentTypeHeaderValue {

	protected String value;

	protected Map<String, String> valueMap = new HashMap<String, String>();

	private ContentTypeHeaderValue(String value, Map<String, String> vmap) {
		this.value = value;
		this.valueMap = vmap;
	}

	public static ContentTypeHeaderValue parse(String value) {
		if (value == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		String[] vs = value.split(";");
		for (String s : vs) {
			s = s.trim();
			String[] ss = s.split("=");

			String keyI = ss[0];
			keyI = keyI.toLowerCase();// lower case?
			String valueI = (ss.length >= 2 ? ss[1] : null);
			map.put(keyI, valueI);

		}
		ContentTypeHeaderValue rt = new ContentTypeHeaderValue(value, map);
		return rt;

	}

	public String getCharset() {
		return this.valueMap.get("charset");
	}

}
