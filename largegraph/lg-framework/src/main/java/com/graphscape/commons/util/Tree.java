
package com.graphscape.commons.util;

import java.util.Iterator;
import java.util.List;

/**
 * @author wuzhen
 * 
 */
public abstract class Tree<T> {

	public abstract void clean();
	
	public abstract Node<T> addNode(Path p);

	public abstract Node<T> addNode(Path p, T t);

	public abstract Node<T> getNode(Path p);

	public abstract T getTarget(Path p);

	public abstract List<T> getTargetListInPath(Path p);

	public abstract List<Node<T>> getChildNodeList(Path p);

	public abstract Node<T> getOrCreateNode(Path p);
	
	public abstract Iterator<Node<T>> iterator();

}
