package com.graphscape.commons.handling;

import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.LifeCycleI;

public interface ProcessorI<S, T> extends LifeCycleI {

	public FutureI<T> process(S s);

}
