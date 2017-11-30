/**
 * 
 */
package com.graphscape.gwt.core.support;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.SimpleValueDeliverI;
import com.graphscape.gwt.core.ModelI.Location;

/**
 * @author wuzhen
 * 
 */
public abstract class CompositeSimpleValueDeliverSupport<S, T> extends
		SimpleValueDeliverSupport<S, T> {

	private List<SimpleValueDeliverI<S, T>> deliverList = new ArrayList<SimpleValueDeliverI<S, T>>();

	/**
	 * @param model2
	 * @param loc2
	 */
	public CompositeSimpleValueDeliverSupport(ModelI model2, Location loc2) {
		super(model2, loc2);
	}

	protected void addDeliver(SimpleValueDeliverI<S, T> sd) {
		sd.mapDefault(this.defaultConverter);
		sd.mapDefault(this.defaultTargetValue);
		sd.mapValue(this.valueMap);
		this.deliverList.add(sd);//
	}

}
