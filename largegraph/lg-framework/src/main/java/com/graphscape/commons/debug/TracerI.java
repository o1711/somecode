package com.graphscape.commons.debug;

import java.util.List;
import java.util.Stack;

import com.graphscape.commons.lang.HandlerI;

public interface TracerI {

	public void beforeExecute(Object obj, Object ... msg);
	
	public void afterExecute();
	
	public boolean apply(Object obj);
	
	public <T> int applyAll(List<T> list);
	
	public void addAfterPushListener(HandlerI<TracerI> l);
	
	public int getDepth();

	public void onException(Throwable e);
	
	public TracerI disable();
	
}
