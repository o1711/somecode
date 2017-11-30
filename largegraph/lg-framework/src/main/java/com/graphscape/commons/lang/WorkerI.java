package com.graphscape.commons.lang;

import com.graphscape.commons.concurrent.FutureI;

public interface WorkerI<T> {

	public FutureI<T> submit();
}
