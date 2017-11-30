/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.document.Document;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.core.ElementI;
import com.graphscape.largegraph.core.EventContext;
import com.graphscape.largegraph.core.provider.lucene.document.BaseDocument;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentType;
import com.graphscape.largegraph.core.provider.lucene.document.operations.UpdateDocumentOperation;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ElementDocument extends BaseDocument implements ElementI {

	/**
	 * @param t
	 */
	public ElementDocument(DocumentFactory factory, DocumentType type, Document t) {
		super(factory, type, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.HasIdI#getId()
	 */
	@Override
	public String getId() {

		return (String) this.getProperty("id", true);
	}

	@Override
	public void setProperty(String key, Object value) {
		this.documentType.addField(this.target, key, value);

		new UpdateDocumentOperation(this.factory, this.documentType, this.getId(), this.target).execute();

	}

	@Override
	public <X> void setProperty(Entry<String, X> entry) {
		this.setProperty(entry.getKey(), entry.getValue());
	}

	@Override
	public Object getProperty(String key) {
		return this.getProperty(key, false);
	}

	@Override
	public Object getProperty(String key, boolean force) {
		Object rt = this.documentType.getFieldValue(this.target, key);
		if (rt == null && force) {
			throw new GsException("no property found:" + key);
		}
		return rt;

	}

	@Override
	public <X> X getProperty(String key, X def) {
		Object o = this.getProperty(key);
		if (o == null) {
			return def;
		}
		return (X) o;
	}

	@Override
	public int getPropertyAsInt(String key, int def) {
		Object o = this.getProperty(key);
		if (o == null) {
			return def;
		}
		return (Integer) o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.PropertiesI#getPropertyAsBoolean(java.lang
	 * .String, boolean)
	 */
	@Override
	public boolean getPropertyAsBoolean(String key, boolean def) {
		throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.PropertiesI#keyList()
	 */
	@Override
	public List<String> keyList() {
		List<String> kL = this.documentType.getKeyList(this.target);
		return kL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.PropertiesI#setProperties(java.util.Map)
	 */
	@Override
	public <X> void setProperties(Map<String, X> map) {
		for (Map.Entry<String, X> en : map.entrySet()) {
			String key = en.getKey();
			Object value = en.getValue();
			this.setProperty(key, value);

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.PropertiesI#setPropertiesByArray(java.lang
	 * .Object[])
	 */
	@Override
	public void setPropertiesByArray(Object... keyValues) {
		throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.PropertiesI#setProperties(com.graphscape.
	 * commons.lang.PropertiesI)
	 */
	@Override
	public <X> void setProperties(PropertiesI<X> pts) {
		throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.PropertiesI#getAsMap()
	 */
	@Override
	public Map<String, Object> getAsMap() {
		Map<String, Object> rt = new HashMap<String, Object>();

		for (String key : this.keyList()) {
			Object v = this.documentType.getFieldValue(this.target, key);
			rt.put(key, v);
		}
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.PropertiesI#mergeFrom(com.graphscape.commons
	 * .lang.PropertiesI)
	 */
	@Override
	public PropertiesI<Object> mergeFrom(PropertiesI<Object> pts) {
		throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.PropertiesI#copy()
	 */
	@Override
	public PropertiesI<Object> copy() {
		throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.ElementI#getEventHandler()
	 */
	@Override
	public HandlerI<EventContext> getEventHandler() {
		throw new GsException("TODO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.ElementI#setEventHandler(java.lang.String)
	 */
	@Override
	public void setEventHandler(String type) {
		throw new GsException("TODO");
	}

}
