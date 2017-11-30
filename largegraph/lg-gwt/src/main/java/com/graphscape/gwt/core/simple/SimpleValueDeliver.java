/**
 * 
 */
package com.graphscape.gwt.core.simple;

import com.graphscape.gwt.core.ModelI;
import com.graphscape.gwt.core.ModelI.Location;
import com.graphscape.gwt.core.ModelI.ValueDeliverI;
import com.graphscape.gwt.core.core.Event.EventHandlerI;
import com.graphscape.gwt.core.event.ModelValueEvent;
import com.graphscape.gwt.core.support.SimpleValueDeliverSupport;

/**
 * @author wuzhen
 * 
 */
public class SimpleValueDeliver<S, T> extends SimpleValueDeliverSupport<S, T> {

	protected ValuePoint<S> sourcePoint;

	public SimpleValueDeliver(ModelI model1, Location loc1, ModelI model2,
			Location loc2) {
		this(model1, loc1, new ValuePoint<T>(model2, loc2));
	}

	public SimpleValueDeliver(ModelI model1, Location loc1,
			ValueSourceI<T> target) {
		super(target);
		this.sourcePoint = new ValuePoint<S>(model1, loc1);
	}

	@Override
	public ValueDeliverI start() {

		EventHandlerI<ModelValueEvent> eh = new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {

				SimpleValueDeliver.this.deliver((S) e.getValue());

			}
		};

		// deliver the future change.
		this.sourcePoint.model.addValueHandler(this.sourcePoint.location, eh);// listen
																				// to
																				// the
																				// location.

		// deliver the current first value.
		Object obj = this.sourcePoint.getValue();

		this.deliver((S) obj);
		return this;
	}

}
