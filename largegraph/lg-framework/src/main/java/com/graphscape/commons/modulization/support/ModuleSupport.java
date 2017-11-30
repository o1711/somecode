/**
 * 
 */
package com.graphscape.commons.modulization.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.configuration.ConfigurationI.Type;
import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.container.provider.default_.DefaultContainer;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.lang.support.EnvironmentAwareSupport;
import com.graphscape.commons.modulization.ApplicationI;
import com.graphscape.commons.modulization.ComponentFactoryI;
import com.graphscape.commons.modulization.ModuleContext;
import com.graphscape.commons.modulization.ModuleI;
import com.graphscape.commons.modulization.provider.default_.DefaultComponentFactory;
import com.graphscape.commons.modulization.provider.default_.DefaultObjectManager;
import com.graphscape.commons.util.ClassUtil;

/**
 * @author wuzhen
 * 
 */
public abstract class ModuleSupport extends EnvironmentAwareSupport implements ModuleI {

	private static final Logger LOG = LoggerFactory.getLogger(ModuleSupport.class);

	protected String id;

	protected ConfigId providerCfgId;

	protected ConfigurationI providerConfig;

	protected ConfigurationI userConfig;

	protected ContainerI container;

	protected ConfigurationProviderI cprovider;

	protected ComponentFactoryI componentFactory;

	protected ModuleI parent;

	protected List<String> moduleIdList;

	protected List<String> dependencyModuleIdList;

	protected Map<String, ModuleI> moduleMap = new HashMap<String, ModuleI>();

	protected DefaultObjectManager objectManager;

	protected ApplicationI application;

	public ModuleSupport(ConfigId userCfgId, ApplicationI app) {
		this(userCfgId, null, app);
	}

	public ModuleSupport(ConfigId userCfgId, ModuleI parent) {
		this(userCfgId, parent, parent.getApplication());
	}

	private ModuleSupport(ConfigId userCfgId, ModuleI parent, ApplicationI app) {
		this.dependencyModuleIdList = new ArrayList<String>();
		this.application = app;
		this.cprovider = this.application.getEnvironment().getService(ConfigurationProviderI.class);
		this.userConfig = this.cprovider.getConfiguration(userCfgId);
		this.id = this.userConfig.getProperty("module", true);
		this.parent = parent;

	}

	@Override
	public String getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.modulization.ModuleI#getParentModule()
	 */
	@Override
	public ModuleI getParentModule() {
		return this.parent;
	}

	@Override
	public void active(ModuleContext mc) {

		this.providerCfgId = ConfigId.parse(this.id);
		this.providerConfig = this.cprovider.getConfiguration(providerCfgId, false);
		this.container = this.parent == null ? new DefaultContainer(this.application.getEnvironment(), id)
				: new DefaultContainer(this.parent.getContainer(), id);
		this.componentFactory = new DefaultComponentFactory(this);
		this.container.addObject(this.componentFactory);

		this.moduleIdList = new ArrayList<String>();
		this.moduleMap = new HashMap<String, ModuleI>();

		this.objectManager = new DefaultObjectManager(this);

		if (this.providerConfig != null) {// has no provider config
			this.activeProviderConfig(mc, this.providerConfig);
		}
		// active this module
		this.activeInternal(mc);

		// active child module
		this.loadModules(mc);
	}

	/**
	 * Active the components in provider's configuration.
	 * 
	 * @param mc
	 */
	protected void activeProviderConfig(ModuleContext mc, ConfigurationI providerCfg) {
		// check dependency module list
		ConfigurationI dpsCfg = this.providerConfig.getChildConfiguration("dependencies", false);
		if (dpsCfg != null) {
			List<ConfigurationI> cfgL = dpsCfg.getChildConfigurationList("dependency");
			for (ConfigurationI cfg : cfgL) {
				String mid = cfg.getProperty("module", true);
				ModuleI dep = mc.getModule().getApplication().getModule(mid, false);
				if (dep == null) {
					throw new GsException("cannot active module:" + this.id + ",not found module:" + mid);
				}
			}
		}
		// load component configuration,TODO not manditory by config.
		ConfigurationI cpsCfg = this.providerConfig.getChildConfiguration("components", false);
		if (cpsCfg == null) {// not config components
			return;
		}
		// for each component configed:
		List<ConfigurationI> cfgL = cpsCfg.getChildConfigurationList();
		for (ConfigurationI cfg : cfgL) {
			Object obj = this.componentFactory.newComponent(cfg);
			if (LOG.isInfoEnabled()) {
				LOG.info("create component:" + obj + "");
			}

			String mgrName = cfg.getProperty("manager");
			this.objectManager.manage(mgrName, obj);
		}
		//
	}

	@Override
	public ApplicationI getApplication() {

		return this.application;
	}

	public void loadModules(ModuleContext mc) {

		List<ConfigurationI> cCfgL = this.userConfig.getChildConfigurationList(Type.valueOf("container"));

		for (ConfigurationI pc : cCfgL) {
			this.activeChildModule(pc.getId());
		}

	}

	@Override
	public PropertiesI<String> getProperties() {
		ConfigurationI rt = this.providerConfig.getChildConfiguration("properties");

		if (rt == null) {
			return new DefaultProperties<String>();
		}
		return rt;
	}

	/**
	 * Active other non-standard things.
	 * 
	 * @param mc
	 */
	protected abstract void activeInternal(ModuleContext mc);

	protected abstract void deactiveInternal(ModuleContext mc);

	@Override
	public void deactive(ModuleContext ac) {
		this.deactiveInternal(ac);
	}

	@Override
	public ModuleI[] activeChildModules(ConfigId[] ids) {
		ModuleI[] rt = new ModuleI[ids.length];
		for (int i = 0; i < ids.length; i++) {
			ConfigId id = ids[i];
			rt[i] = this.activeChildModule(id);

		}
		return rt;
	}

	@Override
	public ModuleI activeChildModule(ConfigId childCfgId) {
		ConfigurationI userCfg = this.cprovider.getConfiguration(childCfgId, true);
		ConfigurationI providerCfg = userCfg.getPropertyAsConfiguration("module", true);
		String cid = userCfg.getProperty("module", true);

		LOG.info("application:" + this.getApplication().getId() + "add module:" + cid);

		if (null != this.getChildModule(cid)) {
			throw new GsException("exsited plugin:" + cid);
		}

		Class cls = providerCfg.getPropertyAsClass("class", true);
		// instance the module object.
		ModuleI pl = (ModuleI) ClassUtil.newInstance(cls, new Class[] { ConfigId.class, ModuleI.class },
				new Object[] { childCfgId, this });

		ModuleContext pc = new ModuleContext(this);
		pl.active(pc);
		this.moduleIdList.add(cid);
		this.moduleMap.put(cid, pl);//

		return pl;
	}

	public ModuleI getChildModule(String id) {
		return this.moduleMap.get(id);
	}

	@Override
	public ContainerI getContainer() {
		return this.container;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.plugin.PluginManagerI#getPlugin
	 * (java.lang.String, boolean)
	 */
	@Override
	public ModuleI getChildModule(String id, boolean force) {
		ModuleI rt = this.moduleMap.get(id);
		if (force && rt == null) {
			throw new GsException("no this plugin:" + id);
		}
		return rt;
	}

	@Override
	public List<ModuleI> getChildModuleList() {
		return new ArrayList<ModuleI>(this.moduleMap.values());
	}

	@Override
	public ConfigurationProviderI getConfigurationProvider() {
		return this.cprovider;
	}

	@Override
	public ComponentFactoryI getComponentFactory() {

		return this.componentFactory;
	}

	@Override
	public List<String> getDependencyModuleList() {
		return this.dependencyModuleIdList;
	}

	@Override
	public ModuleI findModule(String id, boolean force) {
		if (this.id.equals(id)) {
			return this;
		}
		for (ModuleI cm : this.getChildModuleList()) {
			ModuleI rt = cm.findModule(id, false);
			if (rt != null) {
				return rt;
			}
		}
		if (force) {
			throw new GsException("not found module:" + id);
		}
		return null;

	}
}
