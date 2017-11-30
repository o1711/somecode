package org.ta.common.data;

public class TaStringData extends TaData {

	private String stringValue;

	public TaStringData(String value) {
		this.stringValue = value;
	}

	public String getStringValue() {
		return this.stringValue;
	}

	@Override
	public String toString() {

		return TaStringData.class.getSimpleName() + "(" + this.stringValue
				+ ")";
	}

}
