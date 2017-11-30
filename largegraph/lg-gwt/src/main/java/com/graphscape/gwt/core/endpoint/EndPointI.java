/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.graphscape.gwt.core.endpoint;

import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.data.PropertiesData;
import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.endpoint.Address;
import com.graphscape.gwt.core.endpoint.UserInfo;

/**
 * @author wu
 * 
 */
public interface EndPointI extends UiObjectI {

	public static final String D_NAME = "endpoint";

	public void sendMessage(MessageData req);

	public void sendMessage(MsgWrapper req);

	public Address getUri();
	
	public void open();

	public void close();

	public boolean isOpen();

	public void auth(PropertiesData<Object> pts);

	public void logout();

	public boolean isBond();

	public String getSessionId();

	public UserInfo getUserInfo();

	public void destroy();

}
