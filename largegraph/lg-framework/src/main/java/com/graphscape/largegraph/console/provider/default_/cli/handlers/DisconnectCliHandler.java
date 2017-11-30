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
public class DisconnectCliHandler extends CliHandlerSupport {

	private TimeAndUnit timeout = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);

	public DisconnectCliHandler() {

	}

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		CommandAndLine cl = cc.getCommandLine();
		this.disconnect(cc);
	}

	protected void disconnect(CliContext<LargeGraphConsoleI> cc) {
		LargeGraphConsoleI c = cc.getConsole();

		FutureI<ConsoleI> cf = c.disconnect();
		ErrorInfos eis = new ErrorInfos();

		cf.get(timeout);

		if (!eis.hasError()) {
			cc.writeLine("disconnected");
			return;
		}

	}

}
