package com.graphscape.largegraph.test.support;

import junit.framework.TestCase;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.modulization.ApplicationBuilder;
import com.graphscape.commons.modulization.ApplicationI;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * 
 * @author wuzhen
 * 
 */
public class TestCaseSupport extends TestCase {

	public static TimeAndUnit timeout = TimeAndUnit.parse("10M");//

	protected TestCaseEnvironment serverEnv = new TestCaseEnvironment();

	protected TestCaseEnvironment clientEvn = new TestCaseEnvironment();

	public ApplicationI buildApplication(EnvironmentI env, String cfgId) {
		return ApplicationBuilder.newBuilder().environment(env).configure(ConfigId.parse(cfgId)).build();
	}
}
