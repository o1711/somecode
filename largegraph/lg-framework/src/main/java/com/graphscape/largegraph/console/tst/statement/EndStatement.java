package com.graphscape.largegraph.console.tst.statement;

import com.graphscape.largegraph.console.tst.TstConsoleScenarioI;
import com.graphscape.largegraph.console.tst.TstStatement;

public class EndStatement extends TstStatement {

	public EndStatement(TstConsoleScenarioI sce,int lineNum, String value) {
		super(sce,lineNum, value);
	}

	@Override
	public void execute(TstConsoleScenarioI sce) {

	}

}
