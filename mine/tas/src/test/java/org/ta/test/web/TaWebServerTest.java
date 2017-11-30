package org.ta.test.web;

import junit.framework.TestCase;

import org.ta.common.config.TaConfig;
import org.ta.common.config.TaXmlConfigParser;
import org.ta.trader.manage.web.TaTraderServerWebExporter;

public class TaWebServerTest extends TestCase {

	public void test() {
		String res = "/" + TaWebServerTest.class.getName().replace('.', '/') + ".xml";

		TaConfig root = TaXmlConfigParser.parse(res);

		TaTraderServerWebExporter web = root.getChild("web-server", true).newInstance(TaTraderServerWebExporter.class);

		web.start();
		web.stop();
	}
}
