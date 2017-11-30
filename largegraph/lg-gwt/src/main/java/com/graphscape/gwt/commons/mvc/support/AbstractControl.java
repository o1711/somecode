/**
 * Jun 25, 2012
 */
package com.graphscape.gwt.commons.mvc.support;

import com.graphscape.gwt.commons.event.ActionEvent;
import com.graphscape.gwt.commons.mvc.ActionModelI;
import com.graphscape.gwt.commons.mvc.ControlI;
import com.graphscape.gwt.commons.mvc.ControlManagerI;
import com.graphscape.gwt.core.ContainerI;
import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.ModelI.ValueWrapper;
import com.graphscape.gwt.core.commons.Path;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.efilter.ModelValueEventFilter;
import com.graphscape.gwt.core.event.ModelValueEvent;
import com.graphscape.gwt.core.model.ModelChildProcessorI;
import com.graphscape.gwt.core.support.UiObjectSupport;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractControl extends UiObjectSupport implements ControlI, ModelChildProcessorI {

	protected String name;

	protected ContainerI container;

	public AbstractControl(ContainerI c, String name) {
		super(c, name);
		this.container = c;
		this.name = name;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.uicore.api.gwt.client.support.UiObjectSupport#doAttach()
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public ControlManagerI getManager() {

		return (ControlManagerI) this.parent;

	}

	protected <T extends ControlI> T getControl(Class<T> cls, boolean force) {
		return this.getManager().getControl(cls, force);
	}

	@Override
	public void processChildModelAdd(final ModelI parent, final ModelI cm) {
		if (cm instanceof ActionModelI) {
			this.processChildAModelAdd((ActionModelI) cm);
		}
	}

	protected void processChildAModelAdd(ActionModelI amodel) {
		amodel.addHandler(new ModelValueEventFilter(ActionModelI.L_STATE),
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						AbstractControl.this.handleActionStateChanging(e);
					}
				});
	}

	protected void handleActionStateChanging(ModelValueEvent e) {
		ActionModelI am = (ActionModelI) e.getSource();
		ValueWrapper vw = e.getValueWrapper();

		if (vw.isValue(ActionModelI.TRIGGERED)) {
			vw.setValue(ActionModelI.PROCESSING);// NOTE //TODO raise event.
			Path ap = am.getActionPath();
			new ActionEvent(this, ap).dispatch();

		}

	}

}
