/**
 * Jun 23, 2012
 */
package com.graphscape.gwt.core.provider.default_.json;

import com.graphscape.gwt.core.data.message.MessageData;
import com.graphscape.gwt.core.data.property.ObjectPropertiesData;
import com.graphscape.gwt.core.provider.default_.support.PropertiesJCCSupport;

/**
 * @author wu
 * 
 */
public class MessageJCC extends PropertiesJCCSupport<MessageData> {
	public static final String HEADERS = "_headers";
	public static final String PAYLOADS = "_payloads";

	/** */
	public MessageJCC(FactoryI f) {
		super("M", MessageData.class, f);
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected MessageData convert(ObjectPropertiesData od) {
		//
		ObjectPropertiesData hds = (ObjectPropertiesData) od.getProperty(HEADERS, true);
		ObjectPropertiesData pls = (ObjectPropertiesData) od.getProperty(PAYLOADS, true);

		MessageData rt = new MessageData();
		for (String key : hds.keyList()) {
			String sd = (String) hds.getProperty(key);
			rt.setHeader(key, sd == null ? null : sd);
		}
		rt.setPayloads(pls);
		return rt;
	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected ObjectPropertiesData convert(MessageData t) {
		//
		ObjectPropertiesData rt = new ObjectPropertiesData();
		ObjectPropertiesData hds = new ObjectPropertiesData();

		for (String key : t.getHeaders().keyList()) {
			String value = (t.getHeader(key));
			hds.setProperty(key, value);
		}
		rt.setProperty(HEADERS, hds);
		rt.setProperty(PAYLOADS, t.getPayloads());
		return rt;
	}

}
