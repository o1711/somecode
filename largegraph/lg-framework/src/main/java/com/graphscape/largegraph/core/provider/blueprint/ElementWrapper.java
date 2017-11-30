/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core.provider.blueprint;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.support.PropertiesSupport;
import com.graphscape.commons.util.ClassUtil;
import com.graphscape.largegraph.core.ElementI;
import com.graphscape.largegraph.core.EventContext;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.IndexableGraph;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ElementWrapper<T extends Element> extends PropertiesSupport<Object> implements ElementI {

	protected static final String PK_EVENT_HANDLER = "messageHandler";

	protected T target;

	protected DefaultLargeGraph largeGraph;
	
	protected IndexableGraph graph;

	/**
	 * @param t
	 */
	protected ElementWrapper(T t, DefaultLargeGraph lg) {
		this.target = t;
		this.graph = lg.graph;
		this.largeGraph = lg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.PropertiesI#setProperty(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setProperty(String key, Object value) {
		this.target.setProperty(key, value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.lang.PropertiesI#getProperty(java.lang.String)
	 */
	@Override
	public Object doGetProperty(String key) {

		return this.target.getProperty(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.PropertiesI#keyList()
	 */
	@Override
	public List<String> keyList() {
		List<String> rt = new ArrayList<String>(this.target.getPropertyKeys());

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.VertexI#getEventHandler()
	 */
	@Override
	public HandlerI<EventContext> getEventHandler() {
		String cls = (String) this.getProperty(PK_EVENT_HANDLER, true);
		HandlerI<EventContext> mh = ClassUtil.newInstance(cls);

		return mh;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.core.ElementI#getId()
	 */
	@Override
	public String getId() {
		return (String) this.target.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.ElementI#setEventHandler(java.lang.String)
	 */
	@Override
	public void setEventHandler(String type) {
		this.setProperty(PK_EVENT_HANDLER, type);
	}

}
