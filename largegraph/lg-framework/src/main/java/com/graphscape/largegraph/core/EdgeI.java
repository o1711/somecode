/**
 * Dec 8, 2013
 */
package com.graphscape.largegraph.core;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface EdgeI extends ElementI {

	public Label getLabel();
	
	public VertexI getTailVertex();

	public VertexI getHeadVertex();

}
