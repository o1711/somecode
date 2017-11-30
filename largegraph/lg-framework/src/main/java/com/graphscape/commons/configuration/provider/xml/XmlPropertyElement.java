package com.graphscape.commons.configuration.provider.xml;

import org.dom4j.Element;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.PropertyResolverI;

/**
 * @author wuzhen
 * 
 */
public class XmlPropertyElement extends XmlElement {

	/**
	 * @param parent
	 * @param ele
	 */
	public XmlPropertyElement(PropertyResolverI pr, XmlElement parent, Element ele, int index) {
		//
		super(pr, parent, ele, index);
		//
	}

	public String getKey() {
		String name = this.element.getName();
		if (name.equals("property")) {
			name = this.element.attributeValue("name");
		}

		return name;
	}


}
