/**
 * Dec 21, 2013
 */
package com.graphscape.largegraph.test.support;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.container.provider.default_.ClassPathXmlConfigurationEnabledEnvironment;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.support.ProxyEnvironment;
import com.graphscape.largegraph.web.provider.default_.HttpServiceProvider;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class TestCaseEnvironment extends ProxyEnvironment {
	public static final int WEB_PORT = 9002;

	public static final String URI = "http://localhost:" + WEB_PORT + HttpServiceProvider.AJAX.toString();

	/**
	 * @param t
	 */
	public TestCaseEnvironment() {
		super(buildTarget());
	}

	public static EnvironmentI buildTarget() {
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("web.port", WEB_PORT + "");
		EnvironmentI rt = new ClassPathXmlConfigurationEnabledEnvironment(vars);

		return rt;
	}
}
