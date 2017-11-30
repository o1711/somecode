/**
 *  Feb 8, 2013
 */
package com.graphscape.gwt.commons.provider.default_.widget.tab;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.widget.stack.StackWI;
import com.graphscape.gwt.commons.widget.tab.TabWI;
import com.graphscape.gwt.core.dom.ElementWrapper;

/**
 * @author wuzhen
 * 
 */
public abstract class TabberLayout extends ElementWrapper {

	protected boolean isReverse;

	/**
	 * @param c
	 * @param element
	 */
	public TabberLayout(Element element, String cname, boolean rev) {
		super(element);
		element.addClassName(cname);
		this.isReverse = rev;
	}

	protected Element createTr(Element tbody) {
		Element tr = DOM.createTR();
		DOM.appendChild(tbody, tr);
		return tr;
	}

	protected Element createTd(Element tr, String cname) {
		Element td = DOM.createTD();
		DOM.appendChild(tr, td);
		td.addClassName(cname);
		return td;
	}

	protected Element createTrTd(Element tbody, String cname) {
		Element tr = this.createTr(tbody);
		Element td = this.createTd(tr, cname);
		return td;
	}

	public abstract void setStack(StackWI cw);

	public abstract void addTab(TabWI tab);

	public abstract void removeTab(TabWI tab);

	public abstract void afterTabAddOrRemove();

}
