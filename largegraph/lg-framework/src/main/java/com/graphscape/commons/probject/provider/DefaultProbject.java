/**
 * Jan 22, 2014
 */
package com.graphscape.commons.probject.provider;

import java.util.List;

import com.graphscape.commons.lang.support.PropertiesSupport;
import com.graphscape.commons.probject.ProbjectI;
import com.graphscape.commons.record.RecordType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultProbject extends PropertiesSupport<Object> implements ProbjectI {
	protected RecordType type;

	protected DefaultProbjectStorage storage;

	protected String id;

	public DefaultProbject(String k, DefaultProbjectStorage storage) {
		this.id = k;
		this.storage = storage;
	}

	@Override
	public void setProperty(String key, Object value) {
		this.storage.setProperty(this.id, key, value);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.PropertiesI#keyList()
	 */
	@Override
	public List<String> keyList() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.probase.ProdataI#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	protected Object doGetProperty(String key) {
		return this.storage.getProperty(this.id, key);
	}

}
