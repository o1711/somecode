/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CliHandlerI;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CollectionCliHandler extends CliHandlerSupport {

	List<CliHandlerI> handlerList = new ArrayList<CliHandlerI>();

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {
		for (CliHandlerI c : this.handlerList) {
			c.handle(cc);

		}
	}

	public void add(CliHandlerI h) {
		this.handlerList.add(h);

	}

}
