/**
 * Dec 29, 2013
 */
package com.graphscape.largegraph.console;

import com.graphscape.commons.cli.ConsoleI;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface LargeGraphConsoleI extends ConsoleI {

	public ConsoleI uri(String uri);

	public LargeGraphI getClientLargeGraph();

	public GraphI getCurrentGraph();

	public FutureI<ConsoleI> connect();

	public FutureI<ConsoleI> disconnect();

	public boolean isConnected();

}
