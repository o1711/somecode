package org.ta.common.cursor;

public interface TaCursor<T> {
	
	public T get();
	
	public boolean next();

	public boolean previous();

	public boolean last();

	public boolean first();

	public void beforeFirst();
	
	public void afterLast();
}
