/**
 * Dec 15, 2013
 */
package com.graphscape.commons.modulization;

import com.graphscape.commons.configuration.ConfigurationI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ParameterResolverI {

	public Object resolve(ConfigurationI cfg);

}
