package org.ta.trader.rating;

import org.ta.common.config.TaConfig;
import org.ta.common.config.TaConfigurable;
import org.ta.common.handler.TaHandler;
import org.ta.common.handler.TaHandlers;
import org.ta.common.lifecycle.TaLifeCycle;
import org.ta.trader.TaSymbol;
import org.ta.trader.rating.oaa.TaOaaRateConnector;

public class TaRateMonitor implements TaHandler<TaRate>, TaLifeCycle,
		TaConfigurable {

	private TaOaaRateConnector rateConnector;
	private TaHandlers<TaRate> tickingHandlers;
	
	public TaRateMonitor() {

		this.rateConnector = new TaOaaRateConnector();
		this.tickingHandlers = new TaHandlers<TaRate>();

	}
	public TaRate getRate(TaSymbol sb){
		return null;
	}
	@Override
	public void handle(TaRate t) {
		this.tickingHandlers.handle(t);
	}

	@Override
	public void start() {
		this.rateConnector.start();
		this.rateConnector.getRateHandlers().add(this);
	}

	@Override
	public void stop() {
		this.rateConnector.stop();
		this.rateConnector.getRateHandlers().remove(this);
	}

	@Override
	public void configure(TaConfig config) {
		this.rateConnector.configure(config.getChild("rateConnector", true));
	}

}
