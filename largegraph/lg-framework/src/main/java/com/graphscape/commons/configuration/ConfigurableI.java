/**
 * Jun 14, 
 */
package com.graphscape.commons.configuration;

/**
 * This is the interface of configurable components in the topology framework.<br>
 * The interface provides a unique interface to pass the configuration of the value pack.
 * <p>
 * The configurable components could get the detail configuration parameters through the <br>
 * ConfigurationI instance.<br>
 *   
 * @author UCA EBC Team
 * @see ConfigurationI 
 *
 */
public interface ConfigurableI {

	public void config(ConfigurationI cfg);
	
	public ConfigurationI getConfiguration();
	
}
