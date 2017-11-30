/**
 * Dec 21, 2013
 */
package com.graphscape.commons.configuration.support;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.PropertyResolverI;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultPropertyResolver implements PropertyResolverI {
	private Map<String, PropertyResolverI> map = new HashMap<String, PropertyResolverI>();

	public DefaultPropertyResolver(EnvironmentI env) {
		this.map.put("environment", new EnvironmentPropertyResolver(env));
	}

	@Override
	public String resolve(ConfigId cfgId, String key, PropertiesI<String> args) {
		String res = args.getProperty("resolver");
		String rt = null;
		if (res == null) {
			rt = args.getProperty("value");
			if (rt == null) {
				throw new GsException("no value ore resolver set for property:" + cfgId + "." + key);
			}
			return rt;
		}
		PropertyResolverI pr = this.map.get(res);
		if (pr == null) {
			throw new GsException("no resolver found:" + res + " for property:" + cfgId + "." + key);
		}

		return pr.resolve(cfgId, key, args);

	}
}
