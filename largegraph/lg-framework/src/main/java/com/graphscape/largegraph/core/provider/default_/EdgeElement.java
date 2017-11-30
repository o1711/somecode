/**
 * Jan 22, 2014
 */
package com.graphscape.largegraph.core.provider.default_;

import com.graphscape.commons.probject.ProbjectI;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class EdgeElement extends ElementSupport implements EdgeI {

	public EdgeElement(ProbjectI t) {
		super(t);
	}

	@Override
	public Label getLabel() {
		return null;
	}

	@Override
	public VertexI getTailVertex() {
		return null;
	}

	@Override
	public VertexI getHeadVertex() {
		return null;
	}


}
