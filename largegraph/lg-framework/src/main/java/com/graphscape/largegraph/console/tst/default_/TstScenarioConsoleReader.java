package com.graphscape.largegraph.console.tst.default_;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.cli.ConsoleReaderI;
import com.graphscape.largegraph.console.tst.TstConsoleScenarioI;
import com.graphscape.largegraph.console.tst.statement.CommandStatement;

public class TstScenarioConsoleReader implements ConsoleReaderI {

	private static final Logger LOG = LoggerFactory.getLogger(TstScenarioConsoleReader.class);

	TstConsoleScenarioI scenario;
	List<CommandStatement> cmdList;

	private int index;

	public TstScenarioConsoleReader(TstConsoleScenarioI scenario, List<CommandStatement> cmdList) {
		this.cmdList = cmdList;
	}

	@Override
	public String readLine() {
		if (index >= cmdList.size()) {
			return null;
		}
		String rt = cmdList.get(index++).getCommand();
		return rt;
	}

	@Override
	public void close() {

	}

}
