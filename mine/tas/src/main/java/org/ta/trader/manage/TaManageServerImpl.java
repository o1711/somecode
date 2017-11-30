package org.ta.trader.manage;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta.common.config.TaConfig;
import org.ta.common.config.TaConfigurable;
import org.ta.common.config.TaException;
import org.ta.common.data.TaObjectData;
import org.ta.trader.hista.TaHistoryDataManager;
import org.ta.trader.hista.TaHistoryDataManagerImpl;
import org.ta.trader.rating.TaRateMonitor;

public class TaManageServerImpl implements TaConfigurable, TaManageServer {

	private static Logger LOG = LoggerFactory
			.getLogger(TaManageServerImpl.class);

	private TaHistoryDataManager windowManager;

	private TaRateMonitor tickingManager;

	private Map<String, TaRequestHandler> handlerMap;

	public TaManageServerImpl() {
		this.windowManager = new TaHistoryDataManagerImpl();

		this.handlerMap = new HashMap<String, TaRequestHandler>();
	}

	@Override
	public void configure(TaConfig config) {

		this.tickingManager = config.getChild("tickingManager", true)
				.newInstance(TaRateMonitor.class);

	}

	@Override
	public TaObjectData process(TaObjectData data) {
		TaObjectData rt = new TaObjectData();
		String handler = data.getPropertyAsString("handler");
		TaRequestHandler h = this.handlerMap.get(handler);
		if (h == null) {
			rt.set("errorCode", "handler.notfound");//
			return rt;
		}

		TaRequestContext tc = new TaRequestContext(data, rt);

		h.handle(tc);//
		return rt;

	}

	@Override
	public void start() {
		this.tickingManager.start();
	}

	@Override
	public void stop() {
		this.tickingManager.stop();
	}

}
