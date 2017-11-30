package com.graphscape.gwt.commons.mvc;

import com.graphscape.gwt.core.core.UiObjectI;

public interface ControlManagerI extends UiObjectI {

	public ControlManagerI addControl(ControlI c);

	public <T extends ControlI> T getControl(Class<T> cls, String name, boolean force);

	public <T extends ControlI> T getControl(Class<T> cls, boolean force);

}
