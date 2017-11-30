package com.graphscape.largegraph.console.tst.statement;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.largegraph.console.tst.TstConsoleScenarioI;

public class CommandStatement extends TstOutputStatement {

	protected int index;

	protected String prompt;

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	protected List<TstOutputStatement> outputStatementList = new ArrayList<TstOutputStatement>();

	public CommandStatement(TstConsoleScenarioI sce, int ln, String value, String prompt, int index) {
		super(sce, ln, value);
		this.index = index;
		this.prompt = prompt;
	}

	public String getCommand() {
		return this.value.substring(this.prompt.length());
	}

	@Override
	public void execute(TstConsoleScenarioI sce) {

	}

	public List<TstOutputStatement> getOutputStatementList() {
		return outputStatementList;
	}

}
