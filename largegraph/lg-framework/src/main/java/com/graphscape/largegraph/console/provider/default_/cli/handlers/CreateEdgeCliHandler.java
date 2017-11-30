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
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CreateEdgeCliHandler extends CliHandlerSupport {

	private TimeAndUnit timeout = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);

	public CreateEdgeCliHandler() {

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
		
		if (!cl.hasOption('l')) {
			cc.getErrorInfos().add("label is missing");
		}
		if (!cl.hasOption('s')) {
			cc.getErrorInfos().add("start vertex is missing");
		}
		if (!cl.hasOption('e')) {
			cc.getErrorInfos().add("end vertex is missing");
		}

		if (cc.getErrorInfos().hasError()) {
			return;
		}
		String labelS = cl.getOptionValue('l');
		String startIdV = cl.getOptionValue('s');
		String endIdV = cl.getOptionValue('e');
		String startId = (String) this.resolveVar(startIdV, cc, true);
		String endId = (String) this.resolveVar(endIdV, cc, true);
		
		if(cc.getErrorInfos().hasError()){
			return;
		}
		EdgeI ed = lg.addEdge(Label.valueOf(labelS), startId, endId);

		if (cl.hasOption('k')) {// id
			// set the return vertex in Variable .
			String var = cl.getOptionValue("k");
			String id = ed.getId();
			cc.getConsole().setAttribute(var, id);
		}
		cc.writeLine("edge added,id:"+ed.getId());
		
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
