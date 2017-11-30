package org.ta.common.config;

/**
 * Mar 27, 2012
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta.common.util.TaClassUtil;

/**
 * @author wuzhen
 * 
 */
public class TaXmlConfigParser {

	private static Logger LOG = LoggerFactory
			.getLogger(TaXmlConfigParser.class);

	private TaXmlConfigParser parent;

	private Element element;

	private TaConfig cfg;

	private TaXmlConfigParser(TaXmlConfigParser p, Element e) {

		this.parent = p;
		this.element = e;
	}

	public static TaConfig parse(InputStream is) {

		Element root = null;
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			root = doc.getRootElement();

		} catch (DocumentException e) {
			throw new TaException(e);
		}

		TaXmlConfigParser cp = new TaXmlConfigParser(null, root);
		return cp.parse();
	}

	public static TaConfig parse(String xml) {
		InputStream is = TaXmlConfigParser.class.getClassLoader().getResourceAsStream(xml);
		if (is == null) {
			throw new TaException("resource not found:" + xml);
		}
		return parse(is);
	}

	public TaConfig getCfg() {
		if (this.cfg == null) {
			this.parse();
		}
		return this.cfg;
	}

	private TaConfig parse() {
		this.parseDefBean();
		for (TaXmlConfigParser ec : this.getChildList()) {
			ec.parse();
		}
		return this.cfg;

	}

	public List<TaXmlConfigParser> getChildList() {
		List<TaXmlConfigParser> rt = new ArrayList<TaXmlConfigParser>();
		for (Object eO : this.element.elements()) {
			Element e = (Element) eO;
			rt.add(new TaXmlConfigParser(this, e));
		}
		return rt;
	}

	private void parseDefBean() {

		String elementName = this.element.getName();
		if (LOG.isTraceEnabled()) {
			LOG.trace("elementName: " + elementName);
		}
		Class<TaConfig> cls = TaConfig.class;
		if (LOG.isTraceEnabled()) {
			LOG.trace("ClassName: " + cls.getName());
		}

		Class<?>[] paramTypes = { String.class };
		Object[] values = { elementName };

		TaConfig cfg = TaClassUtil.newInstance(cls, paramTypes, values);
		this.cfg = cfg;
		if (this.parent != null) {
			this.parent.getCfg().addChild(this.cfg);
		}

		Iterator<?> atrList = this.element.attributeIterator();
		String text = this.element.getText();
		cfg.setProperty("value", text);//
		while (atrList.hasNext()) {
			String attrName = ((Attribute) atrList.next()).getName();

			String value = this.element.attributeValue(attrName);
			cfg.setProperty(attrName, value);
		}

		// name
		if (cfg.getName() == null) {
			throw new TaException("[name is null for element:"
					+ this.getAsText());
		}

		if (cfg.getName().equals(TaConfig.PN_PROPERTIES)) {

			String environment = cfg.getProperty(TaConfig.PN_ENVIRONMENT);

			if (environment != null) {// load environment
				Map<String, String> envs = System.getenv();
				for (Map.Entry<String, String> en : envs.entrySet()) {
					String key = environment + en.getKey();
					TaConfig cI = new TaConfig(key);
					cI.setProperty("value", en.getValue());//
					cfg.addChild(cI);//
				}
			}
		}

	}

	public String getAsText() {
		return this.element.asXML();
	}

}