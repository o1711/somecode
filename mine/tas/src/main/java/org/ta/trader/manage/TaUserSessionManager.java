package org.ta.trader.manage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.ta.trader.TaSymbol;
import org.ta.trader.account.TaAccountInfo;
import org.ta.trader.executing.TaInternalOrderExecutor;
import org.ta.trader.hista.TaHistoryDataManager;

public class TaUserSessionManager {

	private Map<String, TaUserSession> sessionMap;

	private TaHistoryDataManager windowManager;

	private TaInternalOrderExecutor orderManager;

	public TaUserSessionManager(TaHistoryDataManager wm, TaInternalOrderExecutor om) {
		this.sessionMap = new HashMap<String, TaUserSession>();
		this.orderManager = om;
		this.windowManager = wm;
	}

	public TaUserSession getSession(String sessionId) {
		return this.sessionMap.get(sessionId);
	}

	public void remove(String sessionId) {
		this.sessionMap.remove(sessionId);
	}

	public TaUserSession createSession(TaAccountInfo ai, TaSymbol sd) {
		String id = UUID.randomUUID().toString();
		TaUserSession rt = new TaUserSession();
		this.sessionMap.put(id, rt);
		return rt;
	}
	public TaInternalOrderExecutor getOrderExecutor(){
		return this.orderManager;
	}
	public TaHistoryDataManager getWindowManager() {
		return windowManager;
	}
}
