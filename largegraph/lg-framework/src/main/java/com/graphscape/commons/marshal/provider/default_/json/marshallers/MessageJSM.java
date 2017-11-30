/**
 * Jul 17, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json.marshallers;

import com.google.gson.JsonElement;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.json.PropertiesJsonMarshallerSupport;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.message.provider.default_.DefaultMessage;

/**
 * @author wu
 * 
 */
public class MessageJSM extends PropertiesJsonMarshallerSupport<MessageI> implements MarshallerI<JsonElement> {
	public static final String HEADERS = "_headers";
	public static final String PAYLOADS = "_payloads";
	public static final String EIS = "_eis";

	/** */
	public MessageJSM(MarshallingProviderI f) {
		super("M", MessageI.class, f);
	}

	/* */
	@Override
	protected MessageI convert(PropertiesI<Object> pts) {
		PropertiesI<String> hds = (PropertiesI) pts.getProperty(HEADERS);
		PropertiesI<Object> payloads = (PropertiesI) pts.getProperty(PAYLOADS);
		ErrorInfos eis = (ErrorInfos) pts.getProperty(EIS);//
		MessageI rt = null;
		if (eis == null) {
			rt = DefaultMessage.newMessage(hds, payloads);
		} else {
			rt = DefaultMessage.newMessage(hds, payloads, eis);
		}

		return rt;

	}

	/* */
	@Override
	protected PropertiesI<Object> convert(MessageI t) {
		PropertiesI<Object> rt = new DefaultProperties<Object>();
		rt.setProperty(HEADERS, t.getHeaders());
		rt.setProperty(PAYLOADS, t.getPayloads());
		ErrorInfos eis = t.getErrorInfos();
		if (eis.hasError()) {
			rt.setProperty(EIS, eis);
		}

		return rt;

	}

}
