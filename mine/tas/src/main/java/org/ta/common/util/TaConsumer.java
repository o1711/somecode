package org.ta.common.util;

public interface TaConsumer<T> {
	
	boolean visit(T t);
	
}
