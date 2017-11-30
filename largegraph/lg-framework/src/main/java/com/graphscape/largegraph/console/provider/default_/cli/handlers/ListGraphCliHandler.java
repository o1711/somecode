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
public class ListGraphCliHandler extends CliHandlerSupport {

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {
		CommandAndLine cl = cc.getCommandLine();
		LargeGraphConsoleI c = cc.getConsole();
		LargeGraphI lg = c.getClientLargeGraph();

		if (cc.getErrorInfos().hasError()) {
			return;
		}

		String id = cc.getCommandLine().getOptionValue('v');
		
		VertexI v = lg.getVertex(id);
		

		if (v == null) {
			cc.writeLine("not found vertex with id:");
		} else {
			Iterator<GraphI> gIt = v.getGraphIterator();
			while (gIt.hasNext()) {
				GraphI g = gIt.next();
				cc.writeLine("id:" + g.getId() + ";" + g.getAsMap());
			}
		}

	}

}
