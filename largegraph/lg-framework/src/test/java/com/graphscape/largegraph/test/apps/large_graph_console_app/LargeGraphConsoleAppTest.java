package com.graphscape.largegraph.test.apps.large_graph_console_app;

import java.io.PrintStream;

import com.graphscape.commons.modulization.ApplicationI;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.tst.TstConsoleScenarioBuilder;
import com.graphscape.largegraph.console.tst.TstConsoleScenarioI;
import com.graphscape.largegraph.console.tst.TstScenarioResult;
import com.graphscape.largegraph.modules.large_graph_console.Module;
import com.graphscape.largegraph.test.support.TestCaseSupport;

public class LargeGraphConsoleAppTest extends TestCaseSupport {

	public void test() throws Exception {

		ApplicationI a1 = this.buildApplication(this.serverEnv, LargeGraphConsoleAppTest.class.getName()
				+ ".server-application");
		a1.start();

		ApplicationI a2 = this.buildApplication(this.clientEvn, LargeGraphConsoleAppTest.class.getName()
				+ ".console-application");
		a2.start();
		LargeGraphConsoleI console = a2.getRootModule().getContainer()
				.getChildContainer(Module.class.getName(), true).find(LargeGraphConsoleI.class, true);

		TstConsoleScenarioBuilder builder = new TstConsoleScenarioBuilder();
		String res = LargeGraphConsoleAppTest.class.getPackage().getName();
		res = res.replace('.', '/');
		res += "/scenario.1.txt";

		TstConsoleScenarioI tsc = builder.console(console).resource(res).build();

		TstScenarioResult rst = tsc.submit().get();

		PrintStream ps = System.out;
		if (rst.hasError()) {
			ps = System.err;
		}

		ps.println("Test " + (rst.hasError() ? " Failure:" : "Successful:") + rst);

		// Thread.sleep(10000000);

	}
}
