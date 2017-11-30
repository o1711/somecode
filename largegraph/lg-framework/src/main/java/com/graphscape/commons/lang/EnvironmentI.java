/**
 * Dec 21, 2013
 */
package com.graphscape.commons.lang;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface EnvironmentI extends HasAttributeI {

	public <T> T getService(Class<T> cls, boolean force);

	public <T> T getService(Class<T> cls);

	public String getVariable(String key, boolean force);

	public String getVariable(String key);

}
