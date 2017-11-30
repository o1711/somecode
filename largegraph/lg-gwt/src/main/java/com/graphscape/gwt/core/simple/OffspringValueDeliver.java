/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package com.graphscape.gwt.core.simple;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.ModelI.Location;
import com.graphscape.gwt.core.ModelI.ValueDeliverI;
import com.graphscape.gwt.core.model.ModelChildProcessorI;
import com.graphscape.gwt.core.reflect.InstanceOf;
import com.graphscape.gwt.core.support.CompositeSimpleValueDeliverSupport;

/**
 * @author wu
 * 
 */
public class OffspringValueDeliver<S, T> extends
		CompositeSimpleValueDeliverSupport<S, T> implements
		ModelChildProcessorI {
	protected static class SourceValuePoint {
		/**
		 * @param mclass
		 * @param loc1
		 */
		public SourceValuePoint(ModelI model, Class<? extends ModelI> mclass,
				String modelName, Location loc1) {
			this.topModel = model;
			this.modelClass = mclass;// the target class to be found in top
										// model.
			this.modelName = modelName;
			this.location = loc1;
		}

		public Class<? extends ModelI> modelClass;
		public String modelName;
		public ModelI topModel;
		public Location location;

		/**
		 * Nov 17, 2012
		 */
		public boolean isMatch(ModelI child) {
			//
			if (!this.topModel.contains(child, true)) {
				return false;
			}
			if (InstanceOf.isInstance(this.modelClass, child)) {
				if (this.modelName == null
						|| this.modelName.equals(child.getName())) {
					return true;
				}
			}
			return false;
		}

	}

	private SourceValuePoint svp;

	public OffspringValueDeliver(ModelI model1, Class<? extends ModelI> ccls,
			Location loc1, ModelI model2, Location loc2) {
		this(model1, ccls, null, loc1, model2, loc2);
	}

	public OffspringValueDeliver(ModelI model1, Class<? extends ModelI> ccls,
			String mname, Location loc1, ModelI model2, Location loc2) {
		super(model2, loc2);
		this.svp = new SourceValuePoint(model1, ccls, mname, loc1);
	}

	/*
	 * Nov 17, 2012
	 */
	@Override
	public ValueDeliverI start() {
		//
		ModelChildProcessorI.Helper.onAttach(this.svp.topModel, this);
		return this;
	}

	@Override
	public void processChildModelAdd(ModelI parent, ModelI child) {
		//
		ModelChildProcessorI.Helper.onAttach(child, this);
		if (this.svp.isMatch(child)) {
			SimpleValueDeliver<S, T> vd = new SimpleValueDeliver<S, T>(child,
					this.svp.location, this.targetValueSource);
			this.addDeliver(vd);// init
			// and start
			vd.start();//

		}
	}

	@Override
	public void processChildModelRemove(ModelI parent, ModelI child) {

	}

}
