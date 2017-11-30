package com.graphscape.commons.lang;

public interface AdaptableI {
	
	public <T> T getInterface(Class<T> cls);
	
	public <T> T getInterface(Class<T> cls,boolean force);
	
}
