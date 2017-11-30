package org.ta.trader;

import java.util.HashMap;
import java.util.Map;

public class TaSymbol {

	private static Map<String, TaSymbol> SYMBOLMAP = new HashMap<String, TaSymbol>();

	private String stringValue;

	public TaSymbol(String value) {
		this.stringValue = value;
	}

	public String getStringValue() {
		return this.stringValue;
	}

	public static TaSymbol valueOf(String value) {
		TaSymbol rt = SYMBOLMAP.get(value);
		if (rt == null) {
			rt = new TaSymbol(value);
			SYMBOLMAP.put(value, rt);
		}
		return rt;

	}

	@Override
	public String toString() {
		return this.stringValue;
	}

	@Override
	public int hashCode() {
		return this.stringValue.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TaSymbol)) {
			return false;
		}
		return this.stringValue.equals(((TaSymbol) obj).stringValue);
	}

}
