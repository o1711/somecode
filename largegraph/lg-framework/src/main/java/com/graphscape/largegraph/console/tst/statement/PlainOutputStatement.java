package com.graphscape.largegraph.console.tst.statement;

import com.graphscape.largegraph.console.tst.TstConsoleScenarioI;

public class PlainOutputStatement extends TstOutputStatement {

	private CommandStatement promptStatement;

	public PlainOutputStatement(TstConsoleScenarioI sce, int ln, String value, CommandStatement ps) {
		super(sce, ln, value);
		this.promptStatement = ps;
	}

	@Override
	public void execute(TstConsoleScenarioI sce) {

	}

}
