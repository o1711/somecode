/**
 * Dec 22, 2013
 */
package com.graphscape.largegraph.client.provider.default_;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.largegraph.client.ClientI;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.Label;
import com.graphscape.largegraph.core.VertexI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ClientEdge extends ClientElement implements EdgeI {

	private Label label;

	private String startVertexId;

	private String endVertexId;

	/**
	 * @param id
	 * @param t
	 */
	public ClientEdge(ClientI client, String id, Label label, String sId, String eId, PropertiesI<Object> t) {
		super(client, id, t);
		this.label = label;
		this.startVertexId = sId;
		this.endVertexId = eId;
	}

	/**
	 * @param id
	 * @param rt
	 * @return
	 */
	public static EdgeI valueOf(ClientI client, String id, Label label, String sId, String eId,
			PropertiesI<Object> rt) {
		return new ClientEdge(client, id, label, sId, eId, rt);
	}

	@Override
	public VertexI getTailVertex() {
		VertexI rt = this.client.getVertexCache().get(this.startVertexId);
		if(rt == null){
			throw new GsException("TODO,request");			
		}
		return rt;
	}

	@Override
	public VertexI getHeadVertex() {
		VertexI rt = this.client.getVertexCache().get(this.endVertexId);
		if(rt == null){
			throw new GsException("TODO,request");			
		}
		return rt;
	}

	@Override
	public Label getLabel() {
		// TODO Auto-generated method stub
		return this.label;
	}

}
