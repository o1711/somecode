package com.graphscape.commons.debug;

public interface TracableI {
	
	public String getName();
	
	public TracerI getTracer();
	
	public void setTracer(TracerI tracer);
}
