/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 10, 2012
 */
package com.graphscape.gwt.commons.provider.default_.frwk;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.frwk.BodyViewI;
import com.graphscape.gwt.commons.frwk.BottomViewI;
import com.graphscape.gwt.commons.frwk.FrwkViewI;
import com.graphscape.gwt.commons.frwk.HeaderViewI;
import com.graphscape.gwt.commons.mvc.support.ViewSupport;
import com.graphscape.gwt.commons.provider.default_.dom.TDWrapper;
import com.graphscape.gwt.commons.provider.default_.dom.TRWrapper;
import com.graphscape.gwt.commons.provider.default_.dom.TableWrapper;
import com.graphscape.gwt.commons.provider.default_.frwk.BodyView;
import com.graphscape.gwt.commons.provider.default_.frwk.BottomView;
import com.graphscape.gwt.commons.provider.default_.frwk.FrwkView;
import com.graphscape.gwt.commons.provider.default_.frwk.header.HeaderView;
import com.graphscape.gwt.commons.provider.default_.frwk.other.EndpointBusyIndicator;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.core.ElementObjectI;
import com.graphscape.gwt.core.logger.UiLoggerFactory;
import com.graphscape.gwt.core.logger.UiLoggerI;

/**
 * @author wu
 *         <p>
 *         TODO replace ManagersView by Layout/Perspective, use Layout to define
 *         the rule of displaying based on the models.
 *         <p>
 *         There is no need to provide a complex but fixed layout for FrwkView.
 */
public class FrwkView extends ViewSupport implements FrwkViewI {
	private static final UiLoggerI LOG = UiLoggerFactory.getLogger(FrwkView.class);

	private Element top;

	private TDWrapper left;

	private TDWrapper body;

	private TDWrapper right;

	private Element bottom;

	/**
	 * @param ele
	 * @param ctn
	 */
	public FrwkView(ContainerI c) {
		super(c, "frwk", DOM.createDiv());

		top = DOM.createDiv();

		top.addClassName("frwk-outer");

		this.element.appendChild(top);
		{
			TableWrapper table = new TableWrapper();
			this.elementWrapper.append(table);

			TRWrapper tr = table.addTr();
			left = tr.addTd();
			left.addClassName("frwk-left");

			this.body = tr.addTd();

			this.right = tr.addTd();
		}
		
		{//bottom

			this.bottom = DOM.createDiv();
			this.bottom.addClassName("frwk-bottom");
			this.element.appendChild(this.bottom);
		}

		// popup

		//

		HeaderView hv = new HeaderView(this.container);

		hv.parent(this);

		BodyView bv = new BodyView(this.container);
		bv.parent(this);
		
		BottomView tv = new BottomView(this.container);
		tv.parent(this);
		
		//
		EndpointBusyIndicator ebi = new EndpointBusyIndicator(this.container);
		ebi.parent(this);
	}

	/*
	 * Nov 10, 2012
	 */
	@Override
	protected void onAddChild(Element pe, ElementObjectI cw) {

		
		DOM.appendChild(this.top, cw.getElement());
	}

	@Override
	public BodyViewI getBodyView() {
		return this.getChild(BodyViewI.class, true);

	}

	@Override
	public HeaderViewI getHeader() {
		return this.getChild(HeaderViewI.class, true);
	}

	/*
	 *Apr 5, 2013
	 */
	@Override
	public BottomViewI getBottom() {
		// 
		return this.getChild(BottomViewI.class, true);
	}

}
