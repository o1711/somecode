package com.graphscape.commons.configuration.provider.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.PropertyResolverI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.util.ClassLoaderUtil;
import com.graphscape.commons.util.Holder;

/**
 * @author wu
 * 
 */
public class XmlConfigElement extends XmlElement {

	private static Map<String, Map<ConfigId, Holder<XmlConfigElement>>> cache = new HashMap<String, Map<ConfigId, Holder<XmlConfigElement>>>();

	private ConfigId initId;

	/** */
	public XmlConfigElement(PropertyResolverI pr, ConfigId id, Element ele) {
		super(pr, null, ele, 0);
		this.initId = id;
	}

	public XmlConfigElement(PropertyResolverI pr, XmlElement parent, Element ele, int idx) {
		super(pr, parent, ele, idx);

	}

	public String getType() {
		return this.element.getName();
	}

	/**
	 * Try to load the top configuration from a file.
	 * 
	 * @param prefix
	 * @param id
	 * @param force
	 * @return null if not found the file.
	 */
	public static XmlConfigElement tryLoad(PropertyResolverI pr, String prefix, ConfigId id, boolean force) {
		Map<ConfigId, Holder<XmlConfigElement>> pcache = cache.get(prefix);
		if (pcache == null) {
			pcache = new HashMap<ConfigId, Holder<XmlConfigElement>>();
			cache.put(prefix, pcache);
		}
		Holder<XmlConfigElement> ceH = pcache.get(id);

		XmlConfigElement rt = null;
		if (ceH != null) {
			rt = ceH.getTarget();
		} else {

			rt = doTryLoad(pr, prefix, id);

			ceH = new Holder<XmlConfigElement>(rt);

			pcache.put(id, ceH);
		}
		if (force && rt == null) {
			throw new GsException("no resource found for config:" + id + ",prefix:" + prefix);
		}
		return rt;
	}

	/**
	 * try load the top configuration from a file.
	 * 
	 * @param prefix
	 * @param id
	 * @return
	 */
	private static XmlConfigElement doTryLoad(PropertyResolverI pr, String prefix, ConfigId id) {
		SAXReader reader = new SAXReader();

		InputStream is = getInputStream(prefix, id, false);
		if (is == null) {
			return null;
		}
		try {
			Document doc = reader.read(is);

			return new XmlConfigElement(pr, id, doc.getRootElement());
		} catch (DocumentException e) {
			throw new GsException("error when load config,prefix:" + prefix + ",id:" + id, e);
		}
	}

	public static InputStream getInputStream(String prefix, ConfigId id, boolean force) {
		InputStream is = null;
		if ("classpath".equals(prefix)) {

			String resource = "" + id.toString('/') + ".xml";

			is = ClassLoaderUtil.getResourceAsStream(resource);

		} else {
			throw new GsException("not support:" + prefix);
		}

		if (is == null && force) {
			throw new GsException();
		}

		return is;
	}

	/**
	 * @return the properties
	 */
	public PropertiesI<String> getProperties() {
		PropertiesI<String> rt = new DefaultProperties<String>();
		// attributes
		for (Object aO : this.element.attributes()) {
			Attribute a = (Attribute) aO;
			String name = a.getName();

			String value = a.getValue();
			rt.setProperty(name, value);
		}
		// properties
		for (XmlPropertyElement pe : this.getChildList(XmlPropertyElement.class)) {
			String key = pe.getKey();
			Map<String, String> args = pe.getAttributeMap();

			String value = this.propertyResolver.resolve(this.getId(), key, DefaultProperties.valueOf(args));
			rt.setProperty(key, value);

		}

		return rt;

	}

	/**
	 * Find in child,offspring
	 * 
	 * @param id
	 * @return
	 */
	public XmlConfigElement findByConfigId(ConfigId id, boolean force) {

		if (this.getId().equals(id)) {
			return this;
		}
		for (XmlConfigElement ce : this.findList(XmlConfigElement.class, false)) {
			XmlConfigElement xce = ce.findByConfigId(id, false);

			if (xce != null) {
				return xce;
			}

		}
		if (force) {
			throw new GsException("no configuration found,id:" + id + ",in:" + this);
		}
		return null;

	}

	public List<ConfigId> getChildConfigIdList() {
		List<ConfigId> rt = new ArrayList<ConfigId>();
		for (XmlConfigElement ce : this.getChildList(XmlConfigElement.class)) {
			ConfigId id = ce.getId();
			rt.add(id);

		}
		return rt;
	}

	public ConfigId getId() {

		if (this.initId != null) {
			return this.initId;
		}

		XmlConfigElement p = this.getParentConfig(false);

		String name = this.getElementName();

		return ConfigId.valueOf(p == null ? null : p.getId(), name, this.index);
	}
}
