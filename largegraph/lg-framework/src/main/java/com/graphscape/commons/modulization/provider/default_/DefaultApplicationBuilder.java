/**
 * 
 */
package com.graphscape.commons.modulization.provider.default_;

import com.graphscape.commons.configuration.ConfigId;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.configuration.ConfigurationProviderI;
import com.graphscape.commons.lang.EnvironmentI;
import com.graphscape.commons.modulization.ApplicationBuilder;
import com.graphscape.commons.modulization.ApplicationI;
import com.graphscape.commons.util.ClassUtil;

/**
 * @author wuzhen
 * 
 */
public class DefaultApplicationBuilder extends ApplicationBuilder {

	@Override
	public ApplicationI build() {
		// TODO make this
		EnvironmentI env = this.environment;

		ConfigurationI cfg = env.getService(ConfigurationProviderI.class).getConfiguration(this.configId);

		String type = cfg.getProperty("type");
		Class cls = DefaultApplication.class;
		if (type != null) {
			cls = ClassUtil.forName(type, true);
		}

		ApplicationI rt = ClassUtil.newInstance(cls, new Class[] { EnvironmentI.class, ConfigId.class },
				new Object[] { env, this.configId });

		return rt;

	}

}
