/**
 * Dec 21, 2013
 */
package com.graphscape.commons.configuration;

import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface PropertyResolverI {

	public String resolve(ConfigId cfgId, String key, PropertiesI<String> args);

}
