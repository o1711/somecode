/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class AddGraphCliHandler extends CliHandlerSupport {

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		CommandAndLine cl = cc.getCommandLine();

		LargeGraphConsoleI c = cc.getConsole();
		LargeGraphI lg = c.getClientLargeGraph();

		if (cc.getErrorInfos().hasError()) {
			return;
		}
		PropertiesI<Object> pts = new DefaultProperties<Object>();

		GraphI g = lg.addGraph(pts);
		if (cl.hasOption('k')) {
			String key = cl.getOptionValue('k');
			c.setAttribute(key, g.getId());
		}
		cc.writeLine("graph added,id:" + g.getId());

	}

}
