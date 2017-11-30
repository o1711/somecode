package com.graphscape.largegraph.console.tst;

public abstract class TstStatement {

	protected int lineNumber;

	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the scenario
	 */
	public TstConsoleScenarioI getScenario() {
		return scenario;
	}

	protected String value;

	protected TstConsoleScenarioI scenario;

	public TstStatement(TstConsoleScenarioI sce, int ln, String value) {
		this.scenario = sce;
		this.lineNumber = ln;
		this.value = value;
	}

	public abstract void execute(TstConsoleScenarioI sce);

}
