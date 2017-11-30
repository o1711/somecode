/**
 *  Feb 6, 2013
 */
package com.graphscape.gwt.commons.provider.default_.widget.stack;

import com.graphscape.gwt.commons.widget.stack.StackItemI;
import com.graphscape.gwt.commons.widget.stack.StackWI;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.WidgetI;

/**
 * @author wuzhen
 * 
 */
public class StackItemRef implements StackItemI {

	private StackWI stack;

	private boolean isDefault;

	private boolean isSelected;

	private WidgetI managed;

	private Path path;

	public StackItemRef(Path path, StackWI stack, WidgetI managed) {
		super();
		this.path = path;
		this.stack = stack;
		this.managed = managed;
	}

	public boolean isDefaultItem() {
		return this.isDefault;
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public void trySelect(boolean sel) {
		if (this.isSelected() == sel) {
			return;
		}
		this.select(sel);
	}

	@Override
	public void select(boolean sel) {
		this.isSelected = sel;
		this.stack.updateSelect(this);
	}

	public WidgetI getManagedWidget() {
		return this.managed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicommons.api.gwt.client.frwk.ViewReferenceI#getPath()
	 */
	@Override
	public Path getPath() {
		return this.path;
	}

	@Override
	public boolean isSelect() {
		// TODO Auto-generated method stub
		return this.isSelected;
	}

	@Override
	public void remove() {
		
	}
}