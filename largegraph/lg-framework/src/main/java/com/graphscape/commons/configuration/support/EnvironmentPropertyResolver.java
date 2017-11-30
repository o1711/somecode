/**
 * Dec 21, 2013
 */
package com.graphscape.commons.configuration.support;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.PropertyResolverI;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class EnvironmentPropertyResolver implements PropertyResolverI {

	protected EnvironmentI environment;

	public EnvironmentPropertyResolver(EnvironmentI env) {
		this.environment = env;
	}

	@Override
	public String resolve(ConfigId cfgId, String key, PropertiesI<String> args) {
		String var = args.getProperty("var");

		if (var == null) {
			throw new GsException("no var set for property:" + cfgId + "." + key);
		}

		String rt = this.environment.getVariable(var, true);

		return rt;

	}

}
