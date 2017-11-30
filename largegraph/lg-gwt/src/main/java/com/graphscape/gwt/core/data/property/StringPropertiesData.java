/**
 * Jun 23, 2012
 */
package com.graphscape.gwt.core.data.property;

import com.graphscape.gwt.core.UiException;
import com.graphscape.gwt.core.data.PropertiesData;
import com.graphscape.gwt.core.data.property.StringPropertiesData;

/**
 * @author wu
 * 
 */
public class StringPropertiesData extends PropertiesData<String> {
	/* */

	public static StringPropertiesData valueOf(PropertiesData<Object> pts) {
		StringPropertiesData rt = new StringPropertiesData();

		for (String key : pts.keyList()) {
			Object di = pts.getProperty(key);
			if (di instanceof String) {
				String svalue = (String) di;
				rt.setProperty(key, svalue);

			} else {
				throw new UiException("cannot convert:" + di + " to:" + String.class);
			}
		}
		return rt;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof StringPropertiesData)) {
			return false;
		}

		return super.isEquals((StringPropertiesData) obj);

	}

	public void setPropertyAsBoolean(String key, Boolean value) {
		this.setProperty(key, Boolean.toString(value));
	}

	public Boolean getPropertyAsBoolean(String key, boolean def) {
		String rtS = this.getProperty(key);
		return "true".equalsIgnoreCase(rtS) || "Y".equalsIgnoreCase(rtS) || "YES".equalsIgnoreCase(rtS);
	}
}
