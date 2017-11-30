/**
 *  
 */
package com.graphscape.commons.configuration.provider.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.util.ClassUtil;

/**
 * @author wu
 * 
 */
public class XmlConfiguration extends DefaultProperties<String> implements
		ConfigurationI {
	public static Type ROOT = Type.valueOf("config");

	protected ConfigurationProviderI provider;

	protected ConfigId id;

	protected Type type;

	protected List<ConfigId> childIdList = new ArrayList<ConfigId>();

	/**
	 * @param pts
	 */
	public XmlConfiguration(ConfigurationProviderI cp, ConfigId id, Type type) {
		this(cp, id, type, DefaultProperties.empty(String.class),
				new ArrayList<ConfigId>());
	}

	public XmlConfiguration(ConfigurationProviderI cp, ConfigId id, Type type,
			PropertiesI<String> pts, List<ConfigId> childIdSet) {
		this.provider = cp;
		this.id = id;
		this.type = type;
		this.setProperties(pts);
		this.childIdList.addAll(childIdSet);
	}

	public ConfigurationI getPropertyAsConfiguration(String key, boolean force) {
		String cid = this.getProperty(key, force);
		ConfigId cfgId = ConfigId.parse(cid);
		return this.provider.getConfiguration(cfgId);

	}

	public ConfigurationI getReferencedConfiguration(boolean force) {
		String ref = this.getProperty("ref");
		if (ref == null) {
			if (force) {
				throw new GsException("no 'ref' in config:" + this);
			}
			return null;
		}
		ConfigId cfgId = ConfigId.parse(ref);
		return this.provider.getConfiguration(cfgId);

	}

	public Type getType() {
		return type;
	}

	@Override
	public List<ConfigurationI> getChildConfigurationList(String type) {
		return this.getChildConfigurationList(Type.valueOf(type));
	}

	public ConfigurationI getChildConfiguration(Type type, boolean force) {
		List<ConfigurationI> rtL = this.getChildConfigurationList(type);
		if (rtL.isEmpty()) {
			if (force) {
				throw new GsException("no child config fount for type:" + type
						+ ",in parent:" + this);
			}
			return null;
		} else if (rtL.size() == 1) {
			return rtL.get(0);//
		} else {
			throw new GsException("too much child for type:" + type
					+ ",in parent:" + this);

		}

	}

	public ConfigurationI getThisOrReferencedConfiguration() {
		ConfigurationI rt = this.getReferencedConfiguration(false);
		if (rt == null) {
			rt = this;
		}
		return rt;
	}

	@Override
	public ConfigurationI getChildConfiguration(String name) {
		return this.getChildConfiguration(name, false);
	}

	@Override
	public ConfigurationI getChildConfiguration(String name, boolean force) {
		return this.getChildConfiguration(name, 0, force);
	}

	@Override
	public ConfigurationI getChildConfiguration(String name, int index) {
		return this.getChildConfiguration(name, index, false);
	}

	public ConfigurationI getChildConfiguration(String name, int index,
			boolean force) {

		ConfigId cid = this.id.getChild(name, index);
		if (!this.childIdList.contains(cid)) {
			if (force) {
				throw new GsException("no child config:" + name + "[" + index
						+ "] in parent:" + this.id);
			}
			return null;
		}

		ConfigurationI rt = this.provider.getConfiguration(cid);
		return rt;
	}

	public List<ConfigurationI> getChildConfigurationList(Type type) {
		List<ConfigurationI> rt = new ArrayList<ConfigurationI>();
		for (ConfigId cid : this.childIdList) {
			ConfigurationI cc = this.provider.getConfiguration(cid);
			if (cc.getType().equals(type)) {
				rt.add(cc);
			}
		}
		return rt;
	}

	public List<ConfigurationI> getChildConfigurationList() {
		List<ConfigurationI> rt = new ArrayList<ConfigurationI>();
		for (ConfigId cid : this.childIdList) {
			ConfigurationI cc = this.provider.getConfiguration(cid);
			rt.add(cc);
		}
		return rt;
	}

	public ConfigurationProviderI getPropertiesProvider() {

		return this.provider;

	}

	@Override
	public String toString() {
		return "configuration:" + this.getId();
	}

	@Override
	protected Object getSource() {
		return "configuration:" + this.id;
	}

	@Override
	public ConfigId getId() {
		return id;
	}

	public <T> T getPropertyAsNewInstance(String key) {
		return this.getPropertyAsNewInstance(key, false);
	}

	public <T> T getPropertyAsNewInstance(String key, Class[] clss,
			Object[] args) {
		return this.getPropertyAsNewInstance(key, clss, args, false);
	}

	public <T> T getPropertyAsNewInstance(String key, boolean force) {
		Class<T> cls = this.getPropertyAsClass(key, force);
		if (cls == null) {
			return null;
		}
		T rt = ClassUtil.newInstance(cls);

		return rt;

	}

	public <T> T getPropertyAsNewInstance(String key, Class[] clss,
			Object[] args, boolean force) {
		Class<T> cls = this.getPropertyAsClass(key, force);
		if (cls == null) {
			return null;
		}
		T rt = ClassUtil.newInstance(cls, clss, args);

		return rt;
	}

	public List<String> getPropertyAsCSV(String key) {

		List<String> rt = new ArrayList<String>();
		String v = this.getProperty(key);
		if (v == null || v.trim().length() == 0) {
			return rt;
		}

		String[] vs = v.split(",");
		rt.addAll(Arrays.asList(vs));
		return rt;

	}

	public <T> Class<T> getPropertyAsClass(String key) {
		return this.getPropertyAsClass(key, false);
	}

	public <T> Class<T> getPropertyAsClass(String key, boolean force) {
		String cName = this.getProperty(key, false);

		if (cName == null) {
			if (force) {
				throw new GsException("force:" + key + ", in cfg id:" + this.id);
			}
			return null;
		}
		try {
			return ClassUtil.forName(cName);
		} catch (GsException fe) {
			if (fe.getCause() instanceof ClassNotFoundException) {
				throw new GsException("class not found for key:" + key
						+ ",value:" + cName + ",in cfg id:" + this.id);
			}
			throw fe;
		}
	}

	@Override
	public ConfigurationI getChildOrReferencedConfiguration(String name) {
		return this.getChildOrReferencedConfiguration(name, false);
	}

	@Override
	public ConfigurationI getChildOrReferencedConfiguration(String name,
			boolean force) {
		ConfigurationI cc = this.getChildConfiguration(name);
		if (cc == null) {
			if (force) {
				throw new GsException("no child config:" + name + " in parent:"
						+ this.id);
			}
			return null;
		}
		return cc.getThisOrReferencedConfiguration();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.config.ConfigurationI#
	 * getGrandsonListByChildOrReferencedConfiguration(java.lang.String)
	 */
	@Override
	public List<ConfigurationI> getGrandsonListByChildOrReferencedConfiguration(
			String name) {
		ConfigurationI cc = this.getChildConfiguration(name);
		if (cc == null) {
			return new ArrayList<ConfigurationI>();
		}

		return cc.getChildConfigurationList();
	}

}
