/**
 *  Dec 21, 2012
 */
package com.graphscape.gwt.core.event;

import com.graphscape.gwt.core.data.ErrorInfoData;
import com.graphscape.gwt.core.data.ErrorInfosData;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public class EndpointErrorEvent extends EndpointEvent {

	public static final Type<EndpointErrorEvent> TYPE = new Type<EndpointErrorEvent>(EndpointEvent.TYPE, "error");

	protected ErrorInfosData errors = new ErrorInfosData();

	/**
	 * @param type
	 */
	public EndpointErrorEvent(EndPointI c, String message) {
		super(TYPE, c);
		this.errors.add(new ErrorInfoData("unknow", message));
	}

	/**
	 * @return the errors
	 */
	public ErrorInfosData getErrors() {
		return errors;
	}

}
