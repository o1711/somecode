package com.graphscape.largegraph.console.tst;

import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.tst.default_.TstDefaultConsoleScenario;

public class TstConsoleScenarioBuilder {

	private LargeGraphConsoleI console;

	private String resource;

	public TstConsoleScenarioBuilder console(LargeGraphConsoleI console) {
		this.console = console;
		return this;
	}

	public TstConsoleScenarioBuilder resource(String resource) {
		this.resource = resource;
		return this;
	}

	public TstConsoleScenarioI build() {
		TstDefaultConsoleScenario rt = new TstDefaultConsoleScenario(this.console, this.resource);

		return rt;
	}
}
