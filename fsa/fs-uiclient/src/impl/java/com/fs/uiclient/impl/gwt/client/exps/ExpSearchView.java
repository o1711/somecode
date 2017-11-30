/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.ExpLabelView;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.WindowI;
import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.commons.Rectangle;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.event.ScrollEvent;
import com.fs.uicore.api.gwt.client.support.MapProperties;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class ExpSearchView extends ViewSupport implements ExpSearchViewI {

	protected StringEditorI statement;// keywords?

	protected ListI list;

	protected ListI header;

	protected ButtonI search;

	protected ButtonI more;// NOTE

	// protected ButtonI previousPage;

	protected ViewReferenceI managed;

	protected ExpSearchModelI model;

	protected ExpLabelView myexp;// TODO view

	protected boolean noMore;

	/**
	 * @param ele
	 * @param ctn
	 */
	public ExpSearchView(ContainerI ctn) {

		super(ctn, "exps", DOM.createDiv());

		this.model = new ExpSearchModel();

		MapProperties pts = new MapProperties();
		pts.setProperty(ListI.PK_IS_VERTICAL, Boolean.FALSE);
		this.header = this.factory.create(ListI.class, this.getChildName("header"), pts);//
		this.header.parent(this);

		this.statement = this.factory.create(StringEditorI.class);
		this.statement.setProperty(ListI.PK_LIST_ITEM_CLASSNAME, "search-input-td");
		this.statement.getElement().addClassName("search");
		this.statement.parent(this.header);
		this.statement.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent t) {
				ExpSearchView.this.onPhraseChange(t);
			}
		});
		// search button
		this.search = this.factory.create(ButtonI.class);
		this.search.setText(true, UiClientConstants.AP_EXPS_SEARCH.toString());
		this.search.setProperty(ListI.PK_LIST_ITEM_CLASSNAME, "search-button-td");
		this.search.parent(this.header);
		this.search.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ExpSearchView.this.search();
			}
		});
		//
		this.myexp = new ExpLabelView(this.container);
		this.myexp.setProperty(ListI.PK_LIST_ITEM_CLASSNAME, "search-myexp-td");
		this.myexp.parent(this.header);

		this.list = this.factory.create(ListI.class);
		//
		this.list.setName("itemList");// for testing
		this.list.parent(this);

		this.more = this.factory.create(ButtonI.class);
		this.more.getElement().addClassName("more");

		this.more.parent(this);
		this.more.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ExpSearchView.this.onMore();
			}
		});
		this.setNoMore(false);
		// listen window scroll event to trigger the more button.
		WindowI window = this.container.get(WindowI.class, true);
		window.addScrollHandler(new EventHandlerI<ScrollEvent>() {

			@Override
			public void handle(ScrollEvent t) {
				ExpSearchView.this.onWindowScroll(t);

			}
		});

	}

	protected void onWindowScroll(ScrollEvent se) {

		if (this.noMore) {
			return;
		}

		Rectangle rect = this.more.getElementWrapper().getBoundingClientRect();
		Point tl = rect.getTopLeft();
		int top = rect.getTopY();

		WindowI w = (WindowI) se.getSource();
		int wheight = Window.getClientHeight();

		if (top < wheight) {// visible of button
			this.onMore();
		}

	}

	/**
	 * @param t
	 */
	protected void onPhraseChange(ChangeEvent t) {
		String phrase = this.statement.getData();
		this.model.setPhrase(phrase);
	}

	protected void onMore() {
		ActionEvent rt = new ActionEvent(this, Actions.A_EXPS_SEARCH);
		rt.setPayload("isMore", Boolean.TRUE);
		rt.dispatch();
	}

	@Override
	public void addExpItem(ExpItemModel cm) {
		ExpItemModel old = this.model.addExpItem(cm);
		if (old != null) {
			return;// ignore?
		}

		String vname = viewName(cm);
		ExpItemView vi = new ExpItemView(vname, this.getContainer(), cm);
		vi.parent(this.list);// item view in msglist not this.

	}

	protected String viewName(ExpItemModel cm) {
		return "expItem-" + cm.getExpId();
	}

	protected ExpItemView getItemViewByModel(ExpItemModel cm, boolean force) {
		String vname = viewName(cm);
		return (ExpItemView) this.list.getChild(ModelI.class, vname, force);
	}

	/**
	 * Dec 1, 2012
	 */
	public void removeExpItem(ExpItemModel cm) {
		String vname = viewName(cm);
		ExpItemView ev = (ExpItemView) this.list.getChild(ViewI.class, vname, false);

		if (ev == null) {
			return;
		}
		ev.parent(null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI#reset()
	 */
	@Override
	public void reset() {
		this.model.reset();
		this.list.clean(ExpItemView.class);
		this.setNoMore(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI#getExpId(boolean)
	 */
	@Override
	public UserExpModel getExpId(boolean b) {
		// TODO Auto-generated method stub
		return this.model.getExpId(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI#setExpId(java.lang
	 * .String)
	 */
	@Override
	public void setExpId(UserExpModel exp) {
		this.model.setExpId(exp);
		String text = null;
		if (exp != null) {
			text = exp.getTitle();
			this.myexp.setExp(exp.getExpId(), text);
		} else {
			this.myexp.clearExp();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI#getPhrase(boolean)
	 */
	@Override
	public String getPhrase(boolean force) {
		return this.model.getPhrase(force);
	}

	/*
	 * Apr 3, 2013
	 */
	@Override
	public int getSize() {
		//
		return this.model.getExpItemList().size();
	}

	@Override
	public void search() {
		//
		this.reset();//
		this.dispatchActionEvent(Actions.A_EXPS_SEARCH);
	}

	@Override
	public void noMore() {
		this.setNoMore(true);
	}

	public void setNoMore(boolean nomore) {
		this.noMore = nomore;
		this.more.disable(nomore);
		String text = this.noMore ? "/action/no-more" : UiClientConstants.AP_EXPS_MORE.toString();
		this.more.setText(true, text);
	}

}
