package com.graphscape.largegraph.console.tst.statement;

import com.graphscape.commons.util.ObjectUtil;
import com.graphscape.largegraph.console.tst.TstConsoleScenarioI;
import com.graphscape.largegraph.console.tst.TstStatement;

public class TstOutputStatement extends TstStatement {

	public TstOutputStatement(TstConsoleScenarioI sce, int ln, String value) {
		super(sce, ln, value);
	}

	public boolean isMatch(String value) {
		//TODO \r
		return ObjectUtil.nullSafeEquals(this.value + '\r', value);
	}

	public int getOutputLineNumber() {
		return this.lineNumber - this.scenario.getStartLineNumber();
	}

	@Override
	public void execute(TstConsoleScenarioI sce) {

	}

}
