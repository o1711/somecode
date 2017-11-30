/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class UnknowCliHandler extends CliHandlerSupport {

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {
		// HelpFormatter formatter = new HelpFormatter();
		// formatter.printHelp("help", cc.getCommand().getOptions());

		cc.write("help");

	}

}
