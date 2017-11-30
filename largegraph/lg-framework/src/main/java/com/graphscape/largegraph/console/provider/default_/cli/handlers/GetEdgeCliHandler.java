/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GetEdgeCliHandler extends CliHandlerSupport {

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {
		CommandAndLine cl = cc.getCommandLine();
		LargeGraphConsoleI c = cc.getConsole();
		LargeGraphI lg = c.getClientLargeGraph();
		if (!cc.getCommandLine().hasOption('i')) {
			cc.getErrorInfos().add("missing id");
		}

		if (cc.getErrorInfos().hasError()) {
			return;
		}

		String id = cc.getCommandLine().getOptionValue('i');
		
		EdgeI vt = lg.getEdge(id);
		if (vt == null) {
			cc.write("not found vertex with id:");
		} else {
			cc.writeLine("vertex id:" + id);
			cc.write("" + vt.getAsMap());
		}
		cc.writeLine();

	}

}
