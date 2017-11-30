package org.ta.common.util;

import java.lang.management.ManagementFactory;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * The Class JmxUtils.
 */
public final class TaJmxUtils {

	/**
	 * Gets the oS name.
	 * 
	 * @return the oS name
	 */
	public static ObjectName getOSName() {
		try {
			return new ObjectName(ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME);
		} catch (MalformedObjectNameException ex) {
			throw new RuntimeException(ex);
		}
	}
}
