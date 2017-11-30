/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.support;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CliHandlerI;
import com.graphscape.largegraph.console.LargeGraphConsoleI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class CliHandlerSupport implements CliHandlerI<LargeGraphConsoleI> {

	@Override
	public void handle(CliContext<LargeGraphConsoleI> t) {
		this.handleInternal(t);
		
	}

	protected abstract void handleInternal(CliContext<LargeGraphConsoleI> cc);

}
