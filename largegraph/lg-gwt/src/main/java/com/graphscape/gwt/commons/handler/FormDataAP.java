/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.graphscape.gwt.commons.handler;

import com.graphscape.gwt.commons.Constants;
import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.frwk.commons.FormsViewI;
import com.graphscape.gwt.commons.handler.ActionHandlerSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.MsgWrapper;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public abstract class FormDataAP extends ActionHandlerSupport {

	private String form;

	public FormDataAP(ContainerI c) {
		this(c, null);
	}

	public FormDataAP(ContainerI c, String form) {
		super(c);
		this.form = form;
	}

	protected void processFormData(ActionEvent ae, MsgWrapper req) {

		FormsViewI fv = (FormsViewI) ae.getProperty(Constants.AK_FORMS_VIEW);
		ObjectPropertiesData dt = (ObjectPropertiesData) this.getFormData(fv);

		req.setPayloads(dt);
	}

	protected ObjectPropertiesData getFormData(FormsViewI sv) {
		if (this.form == null) {
			return sv.getDefaultForm().getData();
		} else {
			return sv.getForm(this.form).getData();
		}
	}

}
