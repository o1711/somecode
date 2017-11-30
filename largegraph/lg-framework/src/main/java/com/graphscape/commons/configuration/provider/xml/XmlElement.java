package com.graphscape.commons.configuration.provider.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.graphscape.commons.configuration.PropertyResolverI;
import com.graphscape.commons.lang.GsException;

/**
 * @author wu
 * 
 */
public class XmlElement {

	protected XmlElement parent;

	protected Element element;

	protected List<XmlElement> childList = new ArrayList<XmlElement>();

	protected int index;
	
	protected PropertyResolverI propertyResolver;
	
	public XmlElement(PropertyResolverI pr,XmlElement parent, Element ele,int index) {
		this.propertyResolver = pr;
		this.parent = parent;
		this.element = ele;
		this.index = index;
		List<Element> el = ele.elements();// child list
		
		Map<String,Integer> indexMap = new HashMap<String,Integer>();
		
		for (int i = 0; i < el.size(); i++) {
			
			Element ce = el.get(i);
			String ename = ce.getName();
			Integer idx = indexMap.get(ename);
			if(idx==null){
				idx = -1;
			}
			idx+=1;
			indexMap.put(ename, idx);
			XmlElement xe = null;
			if (ename.equals("property")) {
				xe = new XmlPropertyElement(this.propertyResolver,this, ce,idx);
			} else {
				xe = new XmlConfigElement(this.propertyResolver,this, ce,idx);
			}
			this.childList.add(xe);
		}
	}

	/**
	 * @return
	 */
	public String getElementName() {
		return this.element.getName();
	}

	public XmlConfigElement getParentConfig(boolean force) {
		return this.findParent(XmlConfigElement.class, force);
	}

	protected <T extends XmlElement> T findParent(Class<T> cls, boolean force) {
		XmlElement rt = this.parent;
		while (rt != null) {
			if (cls.isInstance(rt)) {
				return (T) rt;
			}
			rt = rt.parent;
		}
		if (force) {
			throw new GsException("not found parent:" + cls + ",in:" + this);
		}
		return null;
	}

	protected <T extends XmlElement> List<T> findList(Class<T> cls, boolean includeme) {
		List<T> rt = new ArrayList<T>();
		if (includeme) {
			if (cls.isInstance(this)) {
				rt.add((T) this);
			}
		}
		for (XmlElement c : this.childList) {
			List<T> cL = c.findList(cls, true);
			rt.addAll(cL);
		}
		return rt;
	}

	protected <T extends XmlElement> List<T> getChildList(Class<T> cls) {
		List<T> rt = new ArrayList<T>();
		for (XmlElement e : this.childList) {
			if (cls.isInstance(e)) {
				rt.add((T) e);
			}
		}
		return rt;
	}
	
	public Map<String,String> getAttributeMap(){
		Map<String,String> rt = new HashMap<String,String>();
		for(int i=0;i<this.element.attributeCount();i++){
			Attribute aI = this.element.attribute(i);
			String key = aI.getName();
			String value = aI.getValue();
			rt.put(key, value);
			
		}
		return rt;
		
	}

}
