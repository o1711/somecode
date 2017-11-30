/**
 * 
 */
package com.graphscape.gwt.core.simple;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.ModelI.Location;
import com.graphscape.gwt.core.ModelI.ValueDeliverI;

/**
 * @author wuzhen
 * 
 */
public class SyncValueDeliver<S, T> extends SimpleValueDeliver<S, T> {

	protected SimpleValueDeliver<T, S> reverseValueDeliver;

	/**
	 * @param target
	 * @param loc
	 */
	public SyncValueDeliver(ModelI srcModel, Location srcLoc, ModelI target,
			Location targetLoc) {
		super(srcModel, srcLoc, target, targetLoc);
		this.reverseValueDeliver = new SimpleValueDeliver<T, S>(target,
				targetLoc, srcModel, srcLoc);
	}

	@Override
	public SyncValueDeliver<S, T> mapValue(S s, T t) {
		super.mapValue(s, t);
		this.reverseValueDeliver.mapValue(t, s);
		return this;
	}

	public SyncValueDeliver<S, T> reverseMapDefault(S defS) {
		this.reverseValueDeliver.mapDefault(defS);
		return this;
	}

	public SimpleValueDeliver<T, S> getReverseValueDeliver() {
		return this.reverseValueDeliver;
	}

	public ValueDeliverI start() {
		super.start();
		this.reverseValueDeliver.start();//
		return this;
	}
}
