/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.util.Iterator;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ListEdgeCliHandler extends CliHandlerSupport {

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
			if (!cl.hasOption('l')) {
				cc.getErrorInfos().add("-l");
				return;
			}
			String labelS = cl.getOptionValue('l');
			Label label = Label.valueOf(labelS);
			Iterator<EdgeI> gIt = v.getEdgeIterator(label);
			while (gIt.hasNext()) {
				EdgeI g = gIt.next();
				cc.writeLine(g.getTailVertex().getId() + "-" + (g.getLabel().toString()) + "->"
						+ g.getHeadVertex().getId());
			}
		}

	}

}
