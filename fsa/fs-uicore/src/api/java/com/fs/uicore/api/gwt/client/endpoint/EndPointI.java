/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package com.fs.uicore.api.gwt.client.endpoint;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.data.message.MessageData;

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
