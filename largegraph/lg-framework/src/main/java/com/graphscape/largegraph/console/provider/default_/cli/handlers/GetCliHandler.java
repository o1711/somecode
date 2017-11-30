/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GetCliHandler extends CliHandlerSupport {

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {
		CommandAndLine cl = cc.getCommandLine();
		LargeGraphConsoleI c = cc.getConsole();
		LargeGraphI lg = c.getClientLargeGraph();
		if (!cc.getCommandLine().hasOption('k')) {
			cc.getErrorInfos().add("missing k");
		}

		if (cc.getErrorInfos().hasError()) {
			return;
		}
		String key = cc.getCommandLine().getOptionValue('k');

		Object v = cc.getConsole().getAttribute(key);
		cc.writeLine(key + "=" + v);

	}

}
