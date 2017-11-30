/**
 * Dec 15, 2013
 */
package com.graphscape.commons.other;

import com.graphscape.commons.lang.WithIdI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface IdBasedManagerI<V> {

	public WithIdI<V> createMember();

	public V getMember(String key);

	public V getMember(String key, boolean force);

	public void addMember(String id, V value);

}
