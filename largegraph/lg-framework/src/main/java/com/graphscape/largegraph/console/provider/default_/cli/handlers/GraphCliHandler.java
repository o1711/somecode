/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.util.concurrent.TimeUnit;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GraphCliHandler extends CliHandlerSupport {

	private TimeAndUnit timeout = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);

	public GraphCliHandler() {

	}

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		CommandAndLine cl = cc.getCommandLine();

		LargeGraphConsoleI c = cc.getConsole();
		LargeGraphI lg = c.getClientLargeGraph();
		// PropertiesI<Object> pts = CreateVertexCliHandler.parseProperties(cl,
		// cc,true);
		if (cc.getErrorInfos().hasError()) {
			return;
		}

		if (!cl.hasOption('i')) {
			cc.getErrorInfos().add("graph id is missing");
		}

		if (!cl.hasOption('a') && !cl.hasOption('r') && !cl.hasOption('c') && !cl.hasOption('d')) {
			cc.getErrorInfos().add("-a add,-r remove or -c clear?");
		}

		if (cc.getErrorInfos().hasError()) {
			return;
		}
		String gIdS = cl.getOptionValue('i');
		String gId = (String) this.resolveVar(gIdS, cc, true);
		if (cl.hasOption('a') || cl.hasOption('r')) {

			String vIdS = cl.getOptionValue('v');

			String vId = (String) this.resolveVar(vIdS, cc, true);

			if (cc.getErrorInfos().hasError()) {
				return;
			}

			GraphI g = lg.getGraph(gId);
			if (cl.hasOption('a')) {
				g.addVertex(vId);
				cc.writeLine("vertex added to group");
			} else if (cl.hasOption('r')) {
				g.removeVertex(vId);
				cc.writeLine("vertex removed from group");
			}

		} else if (cl.hasOption('c')) {
			lg.getGraph(gId).clear();
			cc.writeLine("group deleted");

		} else if (cl.hasOption('d')) {
			lg.deleteGraph(gId);
			cc.writeLine("group deleted");

		}
	}

	protected Object resolveVar(String var, CliContext cc, boolean force) {
		if (!var.startsWith("$")) {
			return var;
		}
		String key = var.substring(1);
		Object obj = cc.getConsole().getAttribute(key);
		if (obj == null && force) {
			cc.getErrorInfos().add("no attribute with key:" + key);
		}
		return obj;
	}

}
