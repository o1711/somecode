/**
 * 
 */
package com.graphscape.commons.lang;

/**
 * @author wuzhen
 * 
 */
public interface CallbackI<T, RT> {

	RT execute(T t);

}
