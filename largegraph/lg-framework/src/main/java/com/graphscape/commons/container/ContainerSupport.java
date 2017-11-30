/**
 * 
 */
package com.graphscape.commons.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.container.provider.default_.ClassPathXmlConfigurationEnabledEnvironment;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.support.EnvironmentAwareSupport;
import com.graphscape.commons.util.Holder;

/**
 * @author wuzhen
 * 
 */

public class ContainerSupport extends EnvironmentAwareSupport implements ContainerI {

	private static final Logger LOG = LoggerFactory.getLogger(ContainerSupport.class);

	private List<Object> objectList = new ArrayList<Object>();

	private Map<Class, Holder<Object>> cache_findList = new HashMap<Class, Holder<Object>>();

	protected String id;

	protected ConfigurationProviderI cprovider;

	protected ContainerI parent;

	protected Map<String, ContainerI> childContainerMap = new HashMap<String, ContainerI>();

	public ContainerSupport(EnvironmentI env, String id) {
		this(env, null, id);
	}

	public ContainerSupport(ContainerI parent, String id) {
		this(parent.getEnvironment(), parent, id);
	}

	private ContainerSupport(EnvironmentI env, ContainerI parent, String id) {
		this.id = id;
		this.setEnvironment(env);//
		this.cprovider = this.envrionment.getService(ConfigurationProviderI.class, true);
		this.parent = parent;
		if (this.parent != null) {

			this.parent.addChild(this);
		}

	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public <T> T find(Class<T> cls) {
		return this.find(cls, false);
	}

	@Override
	public <T> T find(Class<T> cls, boolean force) {
		return find(cls, force, true);
	}

	public <T> T find(Class<T> cls, boolean force, boolean cache) {

		List<T> l = this.findList(cls, cache);
		if (l.isEmpty()) {

			if (force) {
				throw new GsException("not found object with type:" + cls + " in container:" + this.getId()
						+ ",cache:" + cache);
			}
			return null;
		} else if (l.size() == 1) {
			return l.get(0);
		} else {
			throw new GsException("too many object with type:" + cls + ",cache:" + cache);
		}

	}

	@Override
	public <T> List<T> findList(Class<T> cls) {
		return this.findList(cls, false);
	}

	public <T> List<T> findList(Class<T> cls, boolean cache) {
		if (cache) {
			Holder<Object> rtH = (Holder<Object>) this.cache_findList.get(cls);
			if (rtH != null) {
				return (List<T>) rtH.getTarget();
			}
		}

		List<T> rt = new ArrayList<T>();
		for (Object o : this.objectList) {
			if (cls.isInstance(o)) {
				rt.add((T) o);
			}
		}

		if (cache) {
			Holder<Object> rtH = new Holder<Object>(rt);
			this.cache_findList.put(cls, rtH);
		}

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.container.ContainerI#addObject(java
	 * .lang .Object)
	 */
	@Override
	public void addObject(Object obj) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("add object:" + obj + " into container:" + this.id);
		}
		this.objectList.add(obj);
		if (obj instanceof ContainerAwareI) {

			ContainerAwareI ca = (ContainerAwareI) obj;
			ContainerI c = ca.getContainer();

			if (c == null) {
				((ContainerAwareI) obj).setContainer(this);
			} else {
				if (c != this) {
					throw new GsException("the object already has a different container ");
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hp.topology.commons.api.container.ContainerI#
	 * getConfigurationProvider()
	 */
	@Override
	public ConfigurationProviderI getConfigurationProvider() {
		return this.cprovider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.container.ContainerI#getParent()
	 */
	@Override
	public ContainerI getParent() {
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.container.ContainerI#getChildContainerList()
	 */
	@Override
	public List<ContainerI> getChildContainerList() {
		List<ContainerI> rt = new ArrayList<ContainerI>(this.childContainerMap.values());
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.container.ContainerI#getChildContainer(java.lang
	 * .String, boolean)
	 */
	@Override
	public ContainerI getChildContainer(String id, boolean force) {
		ContainerI rt = this.childContainerMap.get(id);
		if (rt == null && force) {
			throw new GsException("no child container:" + id + " found in parent:" + this.id);
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.container.ContainerI#addChild(com.graphscape.commons
	 * .container.ContainerI)
	 */
	@Override
	public void addChild(ContainerI c) {
		ContainerI old = this.getChildContainer(c.getId(), false);
		if (old != null) {
			throw new GsException("already exist child container:" + c.getId() + " in parent:" + this.id);
		}
		this.childContainerMap.put(c.getId(), c);
	}

}
