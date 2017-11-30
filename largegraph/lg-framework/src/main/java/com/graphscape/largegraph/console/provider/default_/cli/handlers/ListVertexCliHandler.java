/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.util.Iterator;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ListVertexCliHandler extends CliHandlerSupport {

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {
		CommandAndLine cl = cc.getCommandLine();
		LargeGraphConsoleI c = cc.getConsole();
		LargeGraphI lg = c.getClientLargeGraph();

		if (cc.getErrorInfos().hasError()) {
			return;
		}

		String id = cc.getCommandLine().getOptionValue('g');

		GraphI g = lg.getGraph(id);

		if (g == null) {
			cc.writeLine("not found graph with id:");
		} else {
			Iterator<VertexI> vIt = g.getVertexIterator();
			while (vIt.hasNext()) {
				VertexI vt = vIt.next();
				cc.writeLine("id:" + vt.getId() + ";" + vt.getAsMap());
			}
		}

	}

}
