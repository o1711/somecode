package org.ta.trader;

public enum TaSide {
	BUY(1), SELL(-1);

	private int intValue;

	private TaSide(int intValue) {
		this.intValue = intValue;
	}

	public boolean isSignEquals(double vector) {
		return this.intValue > 0 && vector > 0 || this.intValue < 0
				&& vector < 0;
	}

	public TaSide getNegative() {
		//
		return this.isUp() ? SELL : BUY;
	}

	public boolean isUp() {
		return this.intValue > 0;
	}

	public double multiple(double vector) {
		if (this.isUp()) {
			return vector;
		} else {
			return -vector;
		}
	}

	public boolean isAllSignEquals(double vectors[]) {
		for (int i = 0; i < vectors.length; i++) {
			if (!isSignEquals(vectors[i])) {
				return false;
			}
		}
		return true;
	}

}
