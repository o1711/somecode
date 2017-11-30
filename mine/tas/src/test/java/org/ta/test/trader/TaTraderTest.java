package org.ta.test.trader;

import org.ta.common.config.TaConfig;
import org.ta.common.config.TaConfigurable;
import org.ta.test.TaTestSupport;
import org.ta.trader.manage.TaManageServer;
import org.ta.trader.manage.TaManageServerImpl;

public class TaTraderTest extends TaTestSupport {

	public void test() {

		String domain = "https://stream-fxpractice.oanda.com";
		String accessToken = "c728e45b850ffaabf1902caee4fe942c-fd16e7134b35bed44fe1c2ace572839e";
		String accountId = "6138532";
		TaConfig config = new TaConfig("traderServerInTest");

		config.addChild(new TaConfig("tickingManager")//
				.addChild(new TaConfig("rateConnector")
						.property("domain", domain)//
						.property("accountId", accountId)//
						.property("accessToken", accessToken)//
				));

		TaManageServer ts = new TaManageServerImpl();
		((TaConfigurable) ts).configure(config);
		ts.start();
		ts.stop();

	}
}
