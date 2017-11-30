/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 18, 2012
 */
package com.graphscape.gwt.test.core.cases;

import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;
import com.graphscape.gwt.core.logger.UiLoggerFactory.Configuration;
import com.graphscape.gwt.test.core.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class LoggerTest extends TestBase {

	public void testLogger() {
		String prefix = "com.test";
		String name = prefix + ".yes.log1";
		UiLoggerI log = UiLoggerFactory.getLogger(name);

		UiLoggerFactory.configure(prefix, UiLoggerI.LEVEL_INFO);
		Configuration cfg = UiLoggerFactory.getConfiguration4Logger(name);
		assertNotNull(cfg);

		assertEquals("default level should be info", UiLoggerI.LEVEL_INFO,
				cfg.getLoggerLevel());

		log.debug("should not be logged for debug level");

		UiLoggerFactory.configure(prefix, UiLoggerI.LEVEL_DEBUG);
		cfg = UiLoggerFactory.getConfiguration4Logger(name);
		assertEquals("log level of " + name + " should be debug",
				UiLoggerI.LEVEL_DEBUG, cfg.getLoggerLevel());

		log.debug("should be logged after configured");

	}
}
