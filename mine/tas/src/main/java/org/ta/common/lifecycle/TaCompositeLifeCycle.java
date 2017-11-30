package org.ta.common.lifecycle;

import java.util.ArrayList;
import java.util.List;

import org.ta.common.config.TaException;

public class TaCompositeLifeCycle implements TaLifeCycle {

	protected List<TaLifeCycle> childList = new ArrayList<TaLifeCycle>();

	@Override
	public void start() {
		for (TaLifeCycle ts : this.childList) {
			ts.start();
		}
	}

	@Override
	public void stop() {
		for (TaLifeCycle ts : this.childList) {
			ts.stop();
		}
	}

}
