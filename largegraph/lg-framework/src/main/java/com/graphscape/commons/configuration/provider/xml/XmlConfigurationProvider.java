package com.graphscape.commons.configuration.provider.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.configuration.support.ConfigurationProviderSupport;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;

/**
 * @author wuzhen
 * 
 */
public class XmlConfigurationProvider extends ConfigurationProviderSupport {

	/**
	 * @param env
	 */
	public XmlConfigurationProvider(EnvironmentI env) {
		super(env);
	}

	private Map<String, Map<ConfigId, XmlConfigElement>> cache = new HashMap<String, Map<ConfigId, XmlConfigElement>>();

	@Override
	public ConfigurationI doGetConfiguration(ConfigId id) {
		//
		List<ConfigId> childIdSet = new ArrayList<ConfigId>();
		Set<String> typeHolder = new HashSet<String>();
		PropertiesI<String> pw = this.loadConfig(id, typeHolder, childIdSet);// load
		if(pw == null){
			return null;
		}

		ConfigurationI.Type type = typeHolder.isEmpty() ? ConfigurationI.ROOT : ConfigurationI.Type
				.valueOf(typeHolder.iterator().next());

		return new XmlConfiguration(this, id, type, pw, childIdSet);
	}

	private Map<ConfigId, XmlConfigElement> loadAllFor(String prefix, ConfigId id) {
		String key = prefix + ":" + id;
		Map<ConfigId, XmlConfigElement> rt = this.cache.get(key);
		if (rt != null) {
			return rt;
		}
		rt = this.doLoadAllFor(prefix, id);
		this.cache.put(key, rt);
		return rt;
	}

	private Map<ConfigId, XmlConfigElement> doLoadAllFor(String prefix, ConfigId id) {
		Map<ConfigId, XmlConfigElement> rt = new HashMap<ConfigId, XmlConfigElement>();
		XmlConfigElement xce = null;//

		while (true) {

			xce = XmlConfigElement.tryLoad(this.propertyResolver,prefix, id, false);

			if (xce != null) {
				rt.put(id, xce);//
			}
			id = id.getParent();
			if(id == null){
				break;
			}
		}

		return rt;

	}

	/* */
	@Override
	protected PropertiesI<String> doLoadResource(String prefix, ConfigId id, Set<String> typeHolder,
			List<ConfigId> childIdSet) {
		//All possiable config elements for this id of config.
		Map<ConfigId, XmlConfigElement> map = this.loadAllFor(prefix, id);
		// find the properties in all the map
		for (XmlConfigElement ce : map.values()) {
			XmlConfigElement xce = ce.findByConfigId(id, false);
			if (xce != null) {//found one element for this id of config, 
				List<ConfigId> cidL = xce.getChildConfigIdList();
				childIdSet.addAll(cidL);
				if (typeHolder != null) {
					String type = xce.getType();
					typeHolder.add(type);
				}

				return DefaultProperties.valueOf(xce.getProperties().getAsMap());

			}
		}

		return null;//not found 

	}

}
