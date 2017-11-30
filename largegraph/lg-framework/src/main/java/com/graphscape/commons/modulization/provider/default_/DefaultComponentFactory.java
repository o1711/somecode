/**
 *  
 */
package com.graphscape.commons.modulization.provider.default_;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.ConfigurableI;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.container.ContainerAwareI;
import com.graphscape.commons.container.ContainerI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.modulization.ComponentFactoryAwareI;
import com.graphscape.commons.modulization.ComponentFactoryI;
import com.graphscape.commons.modulization.ModuleAwareI;
import com.graphscape.commons.modulization.ModuleI;
import com.graphscape.commons.modulization.ParameterResolverI;
import com.graphscape.commons.util.ClassUtil;

/**
 * @author wu
 * 
 */
public class DefaultComponentFactory implements ComponentFactoryI {

	private static Logger LOG = LoggerFactory
			.getLogger(DefaultComponentFactory.class);

	private static final String PR_COMPONENT = "component";

	private ContainerI container;

	private Map<String, Class> typeMap = new HashMap<String, Class>();

	private Map<String, ParameterResolverI> parameterResolverMap = new HashMap<String, ParameterResolverI>();

	private ModuleI module;

	private ConfigurationProviderI cprovider;

	private String id;

	private Map<String, Object> componentMap = new HashMap<String, Object>();

	public DefaultComponentFactory(ModuleI m) {
		this.id = m.getId();
		this.module = m;
		this.cprovider = m.getConfigurationProvider();
		this.container = this.module.getContainer();
		this.parameterResolverMap.put(PR_COMPONENT,
				new ComponentConstructorParameterResolver(this));

	}

	@Override
	public Class getComponentClass(String key, boolean force) {

		Class rt = this.typeMap.get(key);
		if (rt == null && force) {
			throw new GsException("no component type with key:" + key + ",all:"
					+ this.typeMap);
		}
		return rt;

	}

	@Override
	public void addComponentClass(String[] keys, Class cls) {
		for (String key : keys) {
			this.addComponentClass(key, cls);
		}
	}

	@Override
	public void addComponentClass(String key, Class cls) {
		this.addComponentClass(key, cls, false);
	}

	@Override
	public void addComponentClass(String key, Class cls, boolean replace) {
		Class old = this.getComponentClass(key, false);

		if (null != old) {
			if (replace) {
				LOG.warn("replace type:" + key + ",old:" + old + ",new:" + cls);
			} else {

				throw new GsException("component already exist:" + key
						+ ",cls:" + old);
			}
		}

		this.typeMap.put(key, cls);

	}

	@Override
	public <T> T newComponentByChild(ConfigurationI cfg, Class<T> itf,
			boolean force) {

		List<ConfigurationI> cL = cfg.getChildConfigurationList();
		List<ConfigurationI> cc = new ArrayList<ConfigurationI>();
		for (ConfigurationI cI : cL) {
			Class<T> clsI = this.resolveComponentClass(cI);
			if (itf.isAssignableFrom(clsI)) {
				cc.add(cI);
			}
		}

		if (cc.isEmpty()) {
			if (force) {
				throw new GsException("no child with interface:" + itf
						+ " is configured under configuration:" + cfg);
			}
			return null;
		} else if (cc.size() == 1) {
			return this.newComponent(cc.get(0));
		} else {
			throw new GsException("too many child:" + cc
					+ " match the iterface:" + itf + "");
		}

	}

	@Override
	public <T> T newComponent(ConfigurationI cfg) {

		Class<T> cls = this.resolveComponentClass(cfg);
		T rt = this.newComponent(cfg, cls);

		return rt;
	}

	protected <T> Class<T> resolveComponentClass(ConfigurationI cfg) {
		String type = cfg.getProperty("type", false);

		Class<T> cls = this.getComponentClass(type, false);

		if (cls == null) {// not registered
			cls = ClassUtil.forName(type, false);
		}
		if (cls == null) {
			throw new GsException("not found component type:" + type
					+ ",in config:" + cfg.getId());
		}
		return cls;
	}

	@Override
	public <T> T newComponent(ConfigurationI cfg, Class<T> cls) {

		Object[] ps = this.resolveConstructorParameters(cfg);
		Constructor<?> c = this.resolveConstuctor(cfg, cls, ps, true);
		String id = cfg.getProperty("id");
		T rt = this.newComponent(id, c, ps);
		if (rt instanceof ConfigurableI) {
			((ConfigurableI) rt).config(cfg);
		}
		return rt;
	}

	private <T> T newComponent(String id, Constructor<?> constructor,
			Object[] ps) {
		T rt;
		try {
			rt = (T) constructor.newInstance(ps);
		} catch (InstantiationException e) {
			throw GsException.toRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw GsException.toRuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw GsException.toRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw GsException.toRuntimeException(e);
		}

		if (rt instanceof ContainerAwareI) {
			ContainerAwareI ca = (ContainerAwareI) rt;
			ca.setContainer(this.container);
		}

		if (rt instanceof ModuleAwareI) {

			((ModuleAwareI) rt).setModule(this.module);
		}

		if (rt instanceof ComponentFactoryAwareI) {

			((ComponentFactoryAwareI) rt).setComponentFactory(this);
		}

		if (id != null) {
			this.componentMap.put(id, rt);
		}

		return rt;

	}

	public Constructor<?> resolveConstuctor(ConfigurationI cfg, Class cls,
			Object[] ps, boolean force) {

		Constructor<?>[] cs = cls.getConstructors();

		Constructor<?> rt = null;

		for (Constructor<?> c : cs) {// for each constructor

			Class<?>[] pclss = c.getParameterTypes();

			if (pclss.length != ps.length) {
				continue;// not match even the length.check next.
			}
			boolean match = true;
			for (int i = 0; i < ps.length; i++) {
				if (ps[i] == null) {// null always ok for any class?

				} else {

					if (!pclss[i].isInstance(ps[i])) {// not instance
						match = false;
						break;
					}
				}

			}

			if (match) {
				if (rt != null) {

					throw new GsException("found two+ constructor for class:"
							+ cls + " that match parameters:"
							+ Arrays.asList(ps));
				}
				rt = c;
			}

		}
		if (rt == null) {
			if (force) {
				throw new GsException("no constructor found for class:" + cls
						+ " with parameters:" + Arrays.asList(ps)
						+ ",configId:" + cfg.getId());
			}
			return null;
		}

		return rt;
	}

	private Object[] resolveConstructorParameters(ConfigurationI cfg) {
		ConfigurationI ccfg = cfg.getChildConfiguration("constructor");

		if (ccfg == null) {
			return new Object[] {};
		}
		List<ConfigurationI> pcfgL = ccfg
				.getChildConfigurationList("parameter");
		Object[] rt = new Object[pcfgL.size()];
		for (int i = 0; i < pcfgL.size(); i++) {
			ConfigurationI pcfgI = pcfgL.get(i);

			String resv = pcfgI.getProperty("resolver", PR_COMPONENT);
			ParameterResolverI pr = this.parameterResolverMap.get(resv);
			if (pr == null) {
				throw new GsException("no resolver with type:" + resv
						+ " in config:" + pcfgI.getId());
			}
			rt[i] = pr.resolve(pcfgI);
		}
		return rt;
	}

	@Deprecated
	@Override
	public <T> T newComponent(Class<T> cls) {

		try {
			Constructor<?> c = cls.getConstructor(new Class<?>[] {});
			return this.newComponent(null, c, new Object[] {});

		} catch (NoSuchMethodException e) {
			throw new GsException(e);
		} catch (SecurityException e) {
			throw new GsException(e);
		}

	}

	@Override
	public ModuleI getModule() {
		return this.module;
	}

	@Override
	public ComponentFactoryI getParentComponentFactory() {
		ModuleI pm = this.module.getParentModule();
		if (pm == null) {
			return null;
		}
		return pm.getComponentFactory();
	}

	@Override
	public <T> T getComponent(String id, boolean force) {
		T rt = (T) this.componentMap.get(id);
		if (rt == null && force) {
			throw new GsException("no component with id:" + id
					+ " in component factory of module:" + this.module.getId());
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.modulization.ComponentFactoryI#newComponent(com
	 * .graphscape.commons.configuration.ConfigId)
	 */
	@Override
	public <T> T newComponent(ConfigId cfgId) {

		ConfigurationI cfg = this.cprovider.getConfiguration(cfgId, true);

		return this.newComponent(cfg);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.modulization.ComponentFactoryI#getId()
	 */
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public List<ComponentFactoryI> getChildComponentFactoryList() {
		List<ComponentFactoryI> rt = new ArrayList<ComponentFactoryI>();
		List<ModuleI> mL = this.module.getChildModuleList();

		for (ModuleI m : mL) {
			rt.add(m.getComponentFactory());
		}

		return rt;
	}

	@Override
	public ComponentFactoryI findComponentFactory(String id, boolean force) {

		return this.getRootComponentFactory().findComponentFactoryDownward(id,
				force);
	}

	@Override
	public ComponentFactoryI findComponentFactoryDownward(String id,
			boolean force) {
		List<ComponentFactoryI> cL = this.getChildComponentFactoryList();
		ComponentFactoryI rt = null;
		for (ComponentFactoryI cf : cL) {
			if (cf.getId().equals(id)) {
				rt = cf;
			}
		}
		if (rt == null && force) {
			throw new GsException("not found component factory with id:" + id
					+ " downward from component factory:" + this.getId());
		}
		return rt;
	}

	public ComponentFactoryI getRootComponentFactory() {
		ComponentFactoryI p = null;
		ComponentFactoryI rt = this;
		while (true) {
			p = rt.getParentComponentFactory();

			if (p == null) {
				break;
			}
			rt = p;
		}

		return rt;

	}

}
