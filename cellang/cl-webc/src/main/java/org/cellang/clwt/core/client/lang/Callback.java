/**
 * Jun 13, 2012
 */
package org.cellang.clwt.core.client.lang;

/**
 * @author wuzhen
 * 
 */
public interface Callback<T, R> {

	public R execute(T t);

}
