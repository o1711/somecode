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
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DeleteCliHandler extends CliHandlerSupport {

	private TimeAndUnit timeout = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);

	public DeleteCliHandler() {

	}

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		CommandAndLine cl = cc.getCommandLine();

		LargeGraphConsoleI c = cc.getConsole();
		LargeGraphI lg = c.getClientLargeGraph();
		//PropertiesI<Object> pts = CreateVertexCliHandler.parseProperties(cl, cc,true);
		if (cc.getErrorInfos().hasError()) {
			return;
		}
		
		if (!cl.hasOption('g')) {
			cc.getErrorInfos().add("graph is missing");
		}

		if (cc.getErrorInfos().hasError()) {
			return;
		}
		String gIdS = cl.getOptionValue('g');
		
		String gId = (String) this.resolveVar(gIdS, cc, true);
		
		if(cc.getErrorInfos().hasError()){
			return;
		}
		
		lg.deleteGraph(gId);
		
		cc.writeLine("graph deleted.");
		
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
