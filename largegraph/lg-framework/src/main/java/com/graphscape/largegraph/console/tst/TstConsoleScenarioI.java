package com.graphscape.largegraph.console.tst;

import java.util.List;

import com.graphscape.commons.lang.WorkerI;
import com.graphscape.largegraph.console.tst.statement.CommandStatement;

public interface TstConsoleScenarioI extends WorkerI<TstScenarioResult> {

	public int getStartLineNumber();

	public List<CommandStatement> getCommandStatementList();

	public CommandStatement getCommandStatementByIndex(int index);

}
