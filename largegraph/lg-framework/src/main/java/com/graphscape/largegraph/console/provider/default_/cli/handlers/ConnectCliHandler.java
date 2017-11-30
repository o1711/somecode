/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.util.concurrent.TimeUnit;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.commons.cli.ConsoleI;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ConnectCliHandler extends CliHandlerSupport {

	private TimeAndUnit timeout = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);

	public ConnectCliHandler() {

	}

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		CommandAndLine cl = cc.getCommandLine();
		if (!cl.hasOption('u') || !cl.hasOption('U') || !cl.hasOption('p')) {
			cc.writeLine("options:todo");
			return;
		}
		String url = cl.getOptionValue('U');
		String user = cl.getOptionValue('u');
		String password = cl.getOptionValue('p');

		LargeGraphConsoleI c = cc.getConsole();
		c.setAttribute("url", url);
		c.setAttribute("user", user);
		c.setAttribute("password", password);

		cc.writeLine("connecting...");

		FutureI<ConsoleI> cf = c.connect();
		ErrorInfos eis = new ErrorInfos();
		
		cf.get(timeout);
		
		if (!eis.hasError()) {
			cc.writeLine("connected");
			return;
		}
		String line = eis.getErrorCodeListAsString('\n');
		cc.write(line);
	}

}
