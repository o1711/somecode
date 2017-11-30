/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.marshal.MarshallerBuilder;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.util.StringUtil;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;
import com.graphscape.largegraph.core.LargeGraphI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CreateVertexCliHandler extends CliHandlerSupport {

	private TimeAndUnit timeout = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);

	private static MarshallerI objectMarshaller = new MarshallerBuilder().jsonStringProvider()
			.type(PropertiesI.class).build();

	public CreateVertexCliHandler() {
	}

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		CommandAndLine cl = cc.getCommandLine();

		LargeGraphConsoleI c = cc.getConsole();
		LargeGraphI lg = c.getClientLargeGraph();
		PropertiesI<Object> pts = this.parseProperties(cl, cc.getErrorInfos(), true);
		if (cc.getErrorInfos().hasError()) {
			return;
		}

		VertexI vt = lg.addVertex(pts);

		if (cl.hasOption('k')) {// id
			// set the return vertex in Variable .
			String var = cl.getOptionValue("k");
			String id = vt.getId();
			cc.getConsole().setAttribute(var, id);
		}
		cc.writeLine("vertex added,id:" + vt.getId());

	}

	public static PropertiesI<Object> parseProperties(CommandAndLine cl, ErrorInfos eis, boolean force) {
		String[] ps = cl.getArgs();

		if (ps.length == 0) {
			if (force) {
				eis.add("missing properties");
				return null;
			}
			return null;
		}
		String jsonStr = StringUtil.toString(Arrays.asList(ps), ' ');
		jsonStr = jsonStr.trim();
		if (!jsonStr.startsWith("'") || !jsonStr.endsWith("'")) {
			eis.add("properties format error:" + jsonStr);
			return null;
		}
		jsonStr = jsonStr.substring(1, jsonStr.length()-1);
		PropertiesI<Object> rt = (PropertiesI<Object>) objectMarshaller.unmarshal(jsonStr);

		return rt;

	}

}
