/**
 * Jun 30, 2012
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.widget.AbstractWebWidget;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Element;

/**
 * @author wu Root widget
 */
public class DefaultRootWidget extends AbstractWebWidget implements WebWidget, ContainerAware {

	/** */
	public DefaultRootWidget(Container c, String name) {
		super(c, name, getRootElement());
	}

	/* */
	@Override
	public ClientObject getClient(boolean force) {
		ClientObject rt = (ClientObject) this.container.getClient(force);
		return rt;
	}

	/**
	 * Convenience method for getting the document's body element.
	 * 
	 * @return the document's body element
	 */
	public static com.google.gwt.user.client.Element getRootElement() {
		Element ele = Document.get().getElementById("_cellang_root").cast();
		if (ele == null) {
			ele = getBodyElement();
			ele.setInnerText("not found id:_cellang_root");
		} else {
			//ele.setInnerText("Hello world!");
		}

		return ele;

	}

	private static native com.google.gwt.user.client.Element getBodyElement()/*-{
																				return $doc.body;
																				}-*/;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ContainerAwareI#setContainer(com.fs.uicore
	 * .api.gwt.client.Container)
	 */
	@Override
	public void setContainer(Container c) {
		this.container = c;

	}

	@Override
	public Container getContainer() {
		return this.container;//
	}

}
