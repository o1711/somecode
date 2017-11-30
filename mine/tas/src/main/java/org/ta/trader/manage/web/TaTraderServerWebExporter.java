package org.ta.trader.manage.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.ta.common.config.TaConfig;
import org.ta.common.config.TaConfigurable;
import org.ta.common.config.TaException;
import org.ta.common.lifecycle.TaLifeCycle;

public class TaTraderServerWebExporter implements TaLifeCycle, TaConfigurable {
	private Server server;
	private int port;
	private String tradingConfigResource;

	@Override
	public void configure(TaConfig config) {
		this.port = config.getPropertyAsInt("port", 80);
		this.tradingConfigResource = config.getProperty("trader", true);
	}

	@Override
	public void start() {

		this.server = new Server(port);
		ServletHandler handler = new ServletHandler();
		ServletHolder sh = new ServletHolder(TaTraderServerServlet.class);
		sh.setInitParameter(TaTraderServerServlet.TA_TRADINGSERVER_CONFIG,
				this.tradingConfigResource);
		handler.addServletWithMapping(sh, "/trader");
		this.server.setHandler(handler);
		try {
			this.server.start();
		} catch (Exception e1) {
			throw new TaException(e1);
		}

	}

	@Override
	public void stop() {
		try {
			this.server.stop();
		} catch (Exception e) {
			throw new TaException(e);
		}
	}
}
