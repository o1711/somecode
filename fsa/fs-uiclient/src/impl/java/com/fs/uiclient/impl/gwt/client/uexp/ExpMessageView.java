/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 7, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.ElementObjectI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.util.DateUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public abstract class ExpMessageView extends ViewSupport {

	public static interface CreatorI {
		public ExpMessageView create(ContainerI c, ExpMessage msg);
	}

	private static Map<Path, CreatorI> creatorMap = new HashMap<Path, CreatorI>();

	private static CreatorI DEFAULT = new CreatorI() {

		@Override
		public ExpMessageView create(ContainerI c, ExpMessage msg) {
			//
			return new DefaultEMV(c, msg);
		}
	};

	static {
		creatorMap.put(Path.valueOf("/connect/request"), CooperRequestMessageView.CRT);
		creatorMap.put(Path.valueOf("/text-message"), TextMessageView.CRT);
	}

	protected ExpMessage msg;

	protected ElementWrapper messageBodyDiv;

	protected ElementWrapper actionsDiv;

	protected ListI actions;

	protected String nick;

	protected String expBody;

	protected TableWrapper outer;

	/**
	 * @param c
	 * @param ele
	 */
	public ExpMessageView(ContainerI c, ExpMessage msg) {
		super(c, DOM.createDiv());

		this.msg = msg;

		this.elementWrapper.addClassName("expm");
		this.outer = new TableWrapper();
		this.elementWrapper.append(this.outer);
		// first line
		TRWrapper tr0 = this.outer.addTr();
		{
			// image
			{
				TDWrapper td0 = tr0.addTd();
				td0.addClassName("expm-td0");
				// anchor
				UserIconView uiv = new UserIconView(this.container, msg.getAccountId1(), msg.getIcon1AsImageUrl());//				
				td0.append(uiv.getElement());
				uiv.parent(this);//
			}
			// message
			{
				TDWrapper td1 = tr0.addTd();
				td1.addClassName("expm-td1");
				Element ele = DOM.createDiv();
				this.messageBodyDiv = new ElementWrapper(ele);
				this.messageBodyDiv.addClassName("expm-item-body");
				td1.append(ele);

			}
			{
				TDWrapper td2 = tr0.addTd();
				td2.addClassName("expm-td2");

				{
					// time

					Element ele = DOM.createDiv();
					ele.addClassName("expm-timestamp");
					String dateS = DateUtil.format(msg.getTimeStamp(), false);
					ele.setInnerText("" + dateS + "");
					td2.append(ele);
				}
				{
					// nick
					Element ele = DOM.createDiv();
					ele.setInnerText("" + msg.getNick1() + ":");
					ele.addClassName("expm-nick");
					td2.append(ele);

				}
				{
					// exp title1
					Element ele = DOM.createDiv();
					ele.addClassName("expm-exptitle");
					ele.setInnerText("" + msg.getExpTitle1() + "");
					td2.append(ele);
				}
			}
			{
				TDWrapper td3 = tr0.addTd();
				td3.addClassName("expm-td3");
				Element ele = DOM.createDiv();
				td3.append(ele);
				this.actionsDiv = new ElementWrapper(ele);
				this.actionsDiv.addClassName("expm-actions");
				this.actions = this.factory.create(ListI.class);//
				this.actions.parent(this);
			}

		}

		// actions

		//

	}

	public ExpMessage getExpMessage() {
		return this.msg;
	}

	public static ExpMessageView createViewForMessage(ContainerI c, ExpMessage msg) {
		Path path = msg.getPath();
		CreatorI crt = creatorMap.get(path);
		if (crt == null) {
			crt = DEFAULT;
		}
		return crt.create(c, msg);

	}

	/*
	 * Mar 8, 2013
	 */
	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		if (ceo instanceof ListI) {// actions
			this.actionsDiv.append(ceo.getElement());
		} else {
			super.processAddChildElementObject(ceo);
		}
	}

}
