/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 18, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.NodeFields;
import com.fs.uiclient.api.gwt.client.coper.MyExp;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uicore.api.gwt.client.commons.ImageUrl;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class UserExpModel {

	private ObjectPropertiesData target;
	private boolean selected;

	/**
	 * @param name
	 */

	public UserExpModel(ObjectPropertiesData target) {
		this.target = target;
	}

	public String getTitle() {
		return (String) this.target.getProperty(ExpEditViewI.F_TITLE);
	}

	public String getBody() {
		return (String) this.target.getProperty(ExpEditViewI.F_BODY);
	}

	public String getBodyAsHtml() {
		String rt = this.getBody();
		return ExpItemModel.getExpBodyAsHtml(rt);
	}

	public String getExpId() {
		return (String) this.target.getProperty(NodeFields.PK_ID);
	}

	public void select(boolean sel) {
		this.selected = sel;
	}

	public boolean isExpId(String expId) {
		return this.getExpId().equals(expId);
	}

	public boolean isSelected() {
		return this.selected;
	}

	public Long getConnectionCount() {
		return (Long) this.target.getProperty("connectionCount");
	}

	public Long getMessageCount() {

		return (Long) this.target.getProperty("messageCount");
	}

	public Long getNewMessageCount() {

		return (Long) this.target.getProperty("newMessageCount");
	}

	public DateData getTimestamp(boolean force) {
		return (DateData) this.target.getProperty(NodeFields.PK_TIMESTAMP);
	}

	/**
	 * Apr 14, 2013
	 */
	public ImageUrl getIconUrl() {
		//
		String rt = (String) this.target.getProperty(ExpEditViewI.F_ICON);
		return ImageUrl.parse(rt, true);

	}

	public ImageUrl getImageUrl() {
		//
		String rt = (String) this.target.getProperty(ExpEditViewI.F_IMAGE);
		return ImageUrl.parse(rt, true);

	}

	/**
	 * May 3, 2013
	 */
	public String getStatus() {
		//
		return (String) this.target.getProperty(MyExp.F_STATUS);
	}

}
