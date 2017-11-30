/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.largegraph.console.LargeGraphConsoleI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class QuitCliHandler extends DisconnectCliHandler {

	public QuitCliHandler() {

	}

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		LargeGraphConsoleI c = cc.getConsole();
		if(c.isConnected()){
			this.disconnect(cc);
		}
		cc.write("exited");
		cc.writeLine();
	}

}
