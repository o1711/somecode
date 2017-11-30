package com.graphscape.largegraph.console.provider.default_;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.cli.CliHandlerI;
import com.graphscape.commons.cli.ConsoleI;
import com.graphscape.commons.cli.support.ConsoleSupport;
import com.graphscape.commons.client.MessageClientI;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.util.ProxyFuture4;
import com.graphscape.largegraph.client.ClientBuilder;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.client.provider.default_.ClientLargeGraph;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.LargeGraphI;

/**
 * 
 * @author wuzhen
 * 
 */
public class DefaultLargeGraphConsole extends ConsoleSupport<LargeGraphConsoleI> implements LargeGraphConsoleI {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultLargeGraphConsole.class);

	private ClientI client;

	private LargeGraphI largeGraph;

	private String graphId;// TODO

	public DefaultLargeGraphConsole(CliHandlerI<LargeGraphConsoleI> handler) {
		super(handler);

	}

	@Override
	public ConsoleI uri(String uri) {
		this.setAttribute("url", uri);
		return this;
	}

	@Override
	public FutureI<ConsoleI> connect() {
		ClientBuilder builder = new ClientBuilder();
		String url = this.getAttribute("url", true);
		String user = this.getAttribute("user", true);
		String password = this.getAttribute("password", true);

		builder.uri(url);
		this.client = builder.build();
		String credentical = user + ":" + password;
		FutureI<MessageClientI> cf = this.client.connect(credentical);
		ProxyFuture4<MessageClientI, ConsoleI> rt = new ProxyFuture4<MessageClientI, ConsoleI>(cf, this);
		this.largeGraph = new ClientLargeGraph(this.client);

		return rt;
	}

	@Override
	public boolean isConnected() {
		return this.client.isConnected();
	}

	@Override
	public FutureI<ConsoleI> disconnect() {
		if (!this.client.isConnected()) {
			throw new GsException("already disconnected or not connect yet!");
		}
		FutureI<MessageClientI> cf = this.client.disconnect();

		ProxyFuture4<MessageClientI, ConsoleI> rt = new ProxyFuture4<MessageClientI, ConsoleI>(cf, this);
		return rt;
	}

	@Override
	public LargeGraphI getClientLargeGraph() {

		return this.largeGraph;
	}

	@Override
	public GraphI getCurrentGraph() {
		return this.largeGraph.getGraph(this.graphId);
	}

}
