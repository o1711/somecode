/**
 * Jan 22, 2014
 */
package com.graphscape.largegraph.core.provider.default_;

import java.util.Iterator;

import com.graphscape.commons.probject.ProbjectI;
import com.graphscape.largegraph.core.Arrow;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class VertexElement extends ElementSupport implements VertexI {

	public VertexElement(ProbjectI t) {
		super(t);
	}

	@Override
	public Iterator<GraphI> getGraphIterator() {
		return null;
	}

	@Override
	public Iterator<EdgeI> getEdgeIterator(Label label) {
		return null;
	}

	@Override
	public Iterator<EdgeI> getEdgeIterator(Arrow dir, Label label) {
		return null;
	}

	@Override
	public Iterator<EdgeI> getEdgeIterator(Arrow dir) {
		return null;
	}

	@Override
	public void addGraph(String gid) {

	}

}
