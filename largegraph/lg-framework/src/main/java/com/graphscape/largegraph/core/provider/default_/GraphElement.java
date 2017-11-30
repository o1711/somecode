/**
 * Jan 22, 2014
 */
package com.graphscape.largegraph.core.provider.default_;

import java.util.Iterator;

import com.graphscape.commons.probject.ProbjectI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class GraphElement extends ElementSupport implements GraphI {

	public GraphElement(ProbjectI t) {
		super(t);
	}

	@Override
	public void addVertex(String vid) {

	}

	@Override
	public VertexI removeVertex(String vid) {
		return null;
	}

	@Override
	public void clear() {

	}

	@Override
	public Iterator<VertexI> getVertexIterator() {
		return null;
	}

	@Override
	public void destroy() {

	}

	@Override
	public boolean isContainsGraph(String gid) {
		return false;
	}

	@Override
	public Iterator<GraphI> getOverlapedGraphIterator() {
		return null;
	}

	@Override
	public Iterator<GraphI> getNeiborGraphIterator() {
		return null;
	}

	@Override
	public Iterator<GraphI> getInnerGraphIterator() {
		return null;
	}

}
