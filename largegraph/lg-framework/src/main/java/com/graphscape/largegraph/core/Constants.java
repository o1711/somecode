/**
 * 
 */
package com.graphscape.largegraph.core;

import com.graphscape.commons.util.Path;

/**
 * @author wuzhen
 * 
 */
public class Constants {

	public static final Path P_EVENTS = Path.valueOf("events");

	public static final Path P_EVENTS_VERTEX = P_EVENTS.getSubPath("vertex");
	
	public static final Path P_EVENTS_GRAPH = P_EVENTS.getSubPath("graph");

	public static final Path P_EVENTS_EDGE = P_EVENTS.getSubPath("edge");

}
