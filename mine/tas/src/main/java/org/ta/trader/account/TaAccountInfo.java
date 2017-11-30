package org.ta.trader.account;

public class TaAccountInfo {

	private boolean isDemo;

	private double equity;

	private int accoundId;

	public TaAccountInfo(int accountId, boolean isDemo, double equity) {
		this.accoundId = accountId;
		this.isDemo = isDemo;
		this.equity = equity;
	}

	public boolean isDemo() {
		return isDemo;
	}

	public double getEquity() {
		return equity;
	}

	public int getAccoundId() {
		return accoundId;
	}

	public void setEquity(double equity) {
		this.equity = equity;
	}
}
