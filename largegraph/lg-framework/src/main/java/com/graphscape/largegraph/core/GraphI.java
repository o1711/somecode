/**
 * 2013 ����11:14:44
 */
package com.graphscape.largegraph.core;

import java.util.Iterator;

/**
 * 
 * <p>
 * 
 * @author wuzhen0808@gmail.com
 *         <p>
 *         GraphI is a collection of vertex.
 */
public interface GraphI extends ElementI {

	public void addVertex(String vid);

	public VertexI removeVertex(String vid);

	public void clear();

	public Iterator<VertexI> getVertexIterator();

	public void destroy();
	
	public boolean isContainsGraph(String gid);

	public Iterator<GraphI> getOverlapedGraphIterator();
	
	public Iterator<GraphI> getNeiborGraphIterator();
	
	public Iterator<GraphI> getInnerGraphIterator();
}
