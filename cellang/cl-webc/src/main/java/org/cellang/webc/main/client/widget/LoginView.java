package org.cellang.webc.main.client.widget;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.commons.client.frwk.FormViewI;
import org.cellang.clwt.commons.client.frwk.impl.FormsView;
import org.cellang.clwt.commons.client.widget.LabelI;
import org.cellang.clwt.commons.client.widget.StringEditorI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.gwtbridge.GwtKeyDownHandler;
import org.cellang.webc.main.client.AccountsLDW;
import org.cellang.webc.main.client.Actions;
import org.cellang.webc.main.client.RegisteredAccountLDW;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;

/**
 * Session view will add user info in the header view.
 * <p>
 * and open the login window for user auth.
 * 
 * @author wu
 * 
 */
public class LoginView extends FormsView implements LoginViewI {

	private LabelI accountLabel;

	public static String HEADER_ITEM_LOGIN = "login";//

	public LoginView(Container c, String name) {
		super(c, name);

		this.accountLabel = this.factory.create(LabelI.class);
		this.appendElement(this.accountLabel);
		this.addAction(Actions.A_LOGIN_SUBMIT);
		
		FormViewI def = this.getDefaultForm();
		def.addField(FK_EMAIL, String.class);//
		
		Map<String,Object> pts= new HashMap<String,Object>();
		pts.put(StringEditorI.PK_ISPASSWORD, Boolean.TRUE);
		
		def.addField(FK_PASSWORD, String.class,pts);//
		
		def.addField(FK_SAVINGACCOUNT, Boolean.class);
		// actions
		AccountsLDW accs = AccountsLDW.getInstance();
		RegisteredAccountLDW acc1 = accs.getRegistered();
		if (acc1.isValid()) {
			def.setFieldValue(FK_EMAIL, acc1.getEmail());
			def.setFieldValue(FK_PASSWORD, acc1.getPassword());
		}
		
//		this.addGwtEventHandler(KeyDownEvent.getType(), new GwtKeyDownHandler() {
//
//			@Override
//			protected void handleInternal(com.google.gwt.event.dom.client.KeyDownEvent evt) {
//				if (evt.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
//					LoginView.this.dispatchActionEvent(Actions.A_LOGIN_SUBMIT);
//				}
//			}
//		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI#getEmail()
	 */
	@Override
	public String getEmail() {
		return this.getDefaultFormView().getFieldData(FK_EMAIL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI#getPassword()
	 */
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.getDefaultFormView().getFieldData(FK_PASSWORD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI#isSavingAccount()
	 */
	@Override
	public boolean isSavingAccount() {
		return this.getDefaultFormView().getFieldData(FK_SAVINGACCOUNT, Boolean.FALSE);
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public void setEmail(String email) {
		//
		FormViewI def = this.getDefaultForm();
		def.setFieldValue(FK_EMAIL, email);
	}

	/*
	 * Mar 28, 2013
	 */
	@Override
	public void setPassword(String password) {
		//
		FormViewI def = this.getDefaultForm();
		def.setFieldValue(FK_PASSWORD, password);

	}

}
