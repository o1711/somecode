/**
 * Jun 30, 2012
 */
package com.graphscape.gwt.core.provider.default_;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.RootI;
import com.graphscape.gwt.core.ClientI;
import com.graphscape.gwt.core.core.ElementObjectI;
import com.graphscape.gwt.core.support.WidgetBase;

/**
 * @author wu Root widget
 */
public class RootWImpl extends WidgetBase implements RootI, ContainerI.AwareI {

	/** */
	public RootWImpl(ContainerI c, String name) {
		super(c, name, getRootElement());
	}

	/* */
	@Override
	public ClientI getClient(boolean force) {
		ClientI rt = (ClientI) this.container.get(ClientI.class, force);
		return rt;
	}

	@Override
	protected void processAddChildElementObject(ElementObjectI ceo) {
		this.elementWrapper.append(ceo.getElement());//

	}

	/**
	 * Convenience method for getting the document's body element.
	 * 
	 * @return the document's body element
	 */
	public static com.google.gwt.user.client.Element getRootElement() {
		Element ele = Document.get().getElementById("_fs_root").cast();
		if (ele == null) {
			ele = getBodyElement();
		}
		return ele;

	}

	private static native com.google.gwt.user.client.Element getBodyElement()/*-{
																				return $doc.body;
																				}-*/;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.uicore.api.gwt.client.ContainerAwareI#setContainer(com.fs.uicore
	 * .api.gwt.client.ContainerI)
	 */
	@Override
	public void init(ContainerI c) {
		this.container = c;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicore.api.gwt.client.support.UiObjectSupport#getContainer()
	 */
	@Override
	public ContainerI getContainer() {
		return this.container;//
	}

}
