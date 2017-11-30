/**
 * Jun 13, 2012
 */
package com.graphscape.gwt.commons.provider.default_.widget.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.commons.Constants;
import com.graphscape.gwt.commons.provider.default_.dom.TDWrapper;
import com.graphscape.gwt.commons.provider.default_.dom.TRWrapper;
import com.graphscape.gwt.commons.provider.default_.dom.TableWrapper;
import com.graphscape.gwt.commons.widget.list.ListI;
import com.graphscape.gwt.commons.widget.support.LayoutSupport;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.commons.UiPropertiesI;
import com.graphscape.gwt.core.core.ElementObjectI;
import com.graphscape.gwt.core.core.WidgetI;
import com.graphscape.gwt.core.dom.ElementWrapper;

/**
 * @author wuzhen
 * 
 */
public class ListImpl extends LayoutSupport implements ListI {

	protected TableWrapper table;

	protected boolean vertical;

	protected TRWrapper firstTRForHorizental;

	protected Comparator comparator;

	protected List<WidgetI> childList;
	
	public ListImpl(ContainerI c, String name, UiPropertiesI<Object> pts) {
		super(c, name, DOM.createDiv(), pts);
		
		this.childList = new ArrayList<WidgetI>();
		table = new TableWrapper();
		this.elementWrapper.append(table);
		this.vertical = (Boolean) this.getProperty(ListI.PK_IS_VERTICAL, Boolean.TRUE);
		this.comparator = (Comparator) this.getProperty(ListI.PK_COMPARATOR, null);

		String cname = "vlist";
		if (!this.vertical) {
			this.firstTRForHorizental = this.table.addTr();
			cname = "hlist";
		}
		this.element.addClassName(cname);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.widget.list.ListI#getSize()
	 */
	@Override
	public int getSize() {
		return this.getChildWidgetList().size();
	}

	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		
		WidgetI before = null;
		int idx = this.childList.size();//last as default
		if(this.comparator != null){
			
			for( int i=0; i<this.childList.size(); i++){
				WidgetI wi = this.childList.get(0);
				
				if( this.comparator.compare( (WidgetI)ceo,wi) < 0){
					idx = i;
					before = wi;
					break;
				}
			}
		}
		this.childList.add(idx, (WidgetI)ceo);
		String cname = (String)ceo.getProperty(ListI.PK_LIST_ITEM_CLASSNAME);
		if (this.vertical) {
			
			TRWrapper beforeTr = before==null?null:(TRWrapper) before.getProperty("externalParentElement");
			
			TRWrapper tr = this.table.insertTrBefore(beforeTr);
			
			TDWrapper td = tr.addTd();
			td.append(ceo.getElement());
			td.addClassName(Constants.CN_VLIST_ITEM);
			
			if(cname!= null){
				td.addClassName(cname);
			}
			
			ceo.setProperty("externalParentElement", tr);
		} else {//
			TDWrapper td = this.firstTRForHorizental.addTd();
			td.append(ceo.getElement());
			if(cname!= null){
				td.addClassName(cname);
			}
			td.addClassName(Constants.CN_HLIST_ITEM);
			ceo.setProperty("externalParentElement", td);
		}
	}
	
	

	@Override
	protected void onRemoveChild(Element ele, WidgetI cw) {
		ElementWrapper epe = (ElementWrapper) cw.getProperty("externalParentElement");
		//TRWrapper,or TDWrapper
		epe.getElement().removeFromParent();
		this.childList.remove(cw);//NOTE,equals
		super.onRemoveChild(ele, cw);
	}

}
