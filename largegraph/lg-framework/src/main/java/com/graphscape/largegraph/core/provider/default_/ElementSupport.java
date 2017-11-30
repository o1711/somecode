/**
 * Jan 22, 2014
 */
package com.graphscape.largegraph.core.provider.default_;

import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.probject.ProbjectI;
import com.graphscape.commons.probject.support.ProxyProbject;
import com.graphscape.largegraph.core.ElementI;
import com.graphscape.largegraph.core.EventContext;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ElementSupport extends ProxyProbject implements ElementI {

	public ElementSupport(ProbjectI data) {
		super(data);
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.core.ElementI#getEventHandler()
	 */
	@Override
	public HandlerI<EventContext> getEventHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.graphscape.largegraph.core.ElementI#setEventHandler(java.lang.String)
	 */
	@Override
	public void setEventHandler(String type) {
		// TODO Auto-generated method stub
		
	}

}
