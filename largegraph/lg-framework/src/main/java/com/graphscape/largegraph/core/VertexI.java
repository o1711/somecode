/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core;

import java.util.Iterator;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface VertexI extends ElementI {
	
	public Iterator<GraphI> getGraphIterator();
	
	public Iterator<EdgeI> getEdgeIterator(Label label);

	public Iterator<EdgeI> getEdgeIterator(Arrow dir, Label label);

	public Iterator<EdgeI> getEdgeIterator(Arrow dir);
	
	public void addGraph(String gid);
	
}
