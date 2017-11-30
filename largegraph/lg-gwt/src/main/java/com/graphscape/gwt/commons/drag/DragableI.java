/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 19, 2012
 */
package com.graphscape.gwt.commons.drag;

import com.google.gwt.user.client.Element;
import com.graphscape.gwt.core.core.ElementObjectI;

/**
 * @author wuzhen
 *         <p>
 *         see:
 *         <p>
 *         http://chaoticjava.com/posts/drag-and-drop-in-gwt-the-how-to/
 */
public interface DragableI extends ElementObjectI {

	//move must inside in this elemen.
	public Element getOuterMostElement();
	
	//mouse click on this element
	public Element getDragingOnElement();
	
	//move this element
	public Element getMovingElement();
	
	public Element getLogingElement();
	

}
