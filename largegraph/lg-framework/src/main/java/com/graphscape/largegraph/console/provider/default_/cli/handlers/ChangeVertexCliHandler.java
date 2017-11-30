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

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ChangeVertexCliHandler extends CliHandlerSupport {

	private TimeAndUnit timeout = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);

	public ChangeVertexCliHandler() {

	}

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		CommandAndLine cl = cc.getCommandLine();
		if (!cl.hasOption('i')) {
			cc.writeLine("id is missing");
			return;
		}
		String id = cl.getOptionValue('i');
		cc.setAttribute("current_vertex_id", id);
		cc.writeLine("change vertex to:" + id);

	}

}
