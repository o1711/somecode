/**
 * 
 */
package com.graphscape.commons.modulization;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.modulization.provider.default_.DefaultApplicationBuilder;

/**
 * @author wuzhen
 * 
 */
public abstract class ApplicationBuilder {

	protected ConfigId configId;

	protected EnvironmentI environment;
	public static ApplicationBuilder newBuilder() {
		return new DefaultApplicationBuilder();
	}

	public abstract ApplicationI build();
		
	public ApplicationBuilder environment(EnvironmentI env){
		this.environment = env;
		return this;
	}

	/**
	 * @param parse
	 * @return
	 */
	public ApplicationBuilder configure(ConfigId parse) {
		this.configId = parse;
		return this;
	}
}
