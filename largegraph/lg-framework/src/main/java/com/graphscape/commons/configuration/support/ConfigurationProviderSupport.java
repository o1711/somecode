/**
 */
package com.graphscape.commons.configuration.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.configuration.PropertyResolverI;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.util.Holder;

/**
 * @author wuzhen
 * 
 */
public abstract class ConfigurationProviderSupport implements ConfigurationProviderI {

	private Map<ConfigId, Holder<ConfigurationI>> cfgCache;

	private String[] prefix = new String[] { "classpath" };

	protected PropertyResolverI propertyResolver;

	public ConfigurationProviderSupport(EnvironmentI env) {
		this.propertyResolver = new DefaultPropertyResolver(env);
		 
		this.cfgCache = new HashMap<ConfigId, Holder<ConfigurationI>>();
	}

	/*
	
	 */
	@Override
	public void add(ConfigurationI cfg) {
		throw new GsException("read only.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.config.ConfigurationProviderI#
	 * getConfiguration(java.lang.String, boolean)
	 */
	@Override
	public ConfigurationI getConfiguration(ConfigId id) {

		// TODO Auto-generated method stub
		return this.getConfigurationInCache(id, false, true);

	}

	@Override
	public ConfigurationI getConfiguration(ConfigId id, boolean force) {

		return this.getConfigurationInCache(id, force, true);

	}

	private ConfigurationI getConfigurationInCache(ConfigId id, boolean force, boolean cache) {
		if (!cache) {
			return this.doGetConfiguration(id);
		}

		Holder<ConfigurationI> rtH = this.cfgCache.get(id);
		ConfigurationI rt = null;
		if (rtH == null) {// do get and cache

			rt = this.doGetConfiguration(id);
			rtH = new Holder<ConfigurationI>(rt);
			this.cfgCache.put(id, rtH);

		} else {

			rt = rtH.getTarget();

		}
		if (force && rt == null) {
			throw new GsException("not found configuration by id:" + id);
		}
		return rt;

	}

	public PropertiesI<String> loadConfig(ConfigId id, Set<String> typeHolder, List<ConfigId> childIdSet) {
		if (id == null) {
			throw new GsException("id is null");
		}
		PropertiesI<String> pw = null;
		for (String pre : this.prefix) {

			PropertiesI<String> next = this.doLoadResource(pre, id, typeHolder, childIdSet);
			if (next == null) {// no this resource.
				continue;
			}
			if (pw == null) {
				pw = next;
			} else {
				pw = pw.mergeFrom(next);
			}
		}
		// alias
		return pw;

	}

	protected abstract PropertiesI<String> doLoadResource(String prefix, ConfigId id, Set<String> typeHolder,
			List<ConfigId> childIdSet);

	public abstract ConfigurationI doGetConfiguration(ConfigId id);

}
