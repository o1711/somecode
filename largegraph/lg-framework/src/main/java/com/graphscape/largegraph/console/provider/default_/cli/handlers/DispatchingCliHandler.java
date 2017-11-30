/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CliHandlerI;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DispatchingCliHandler extends CliHandlerSupport {

	Map<String, CollectionCliHandler> map = new HashMap<String, CollectionCliHandler>();

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {
		String cmd = cc.getCommandLine().getCommand().getName();
		CollectionCliHandler hdl = this.map.get(cmd);
		
		
		if (hdl == null) {
			cc.write("no this command:" + cmd);			
		}else{
			hdl.handle(cc);			
		}
		

	}

	public void addCliHandler(String cmd, CliHandlerI hdl) {
		CollectionCliHandler cc = this.map.get(cmd);
		if (cc == null) {
			cc = new CollectionCliHandler();
			this.map.put(cmd, cc);
		}

		cc.add(hdl);

	}

}
