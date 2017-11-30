/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json.marshallers;

import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.json.PropertiesJsonMarshallerSupport;

/**
 * @author wu
 * 
 */
public class ObjectPropertiesJSM extends PropertiesJsonMarshallerSupport<PropertiesI> {

	/** */
	public ObjectPropertiesJSM(MarshallingProviderI f) {
		super("O", PropertiesI.class, f, false);
	}

	/* */
	@Override
	public PropertiesI convert(PropertiesI pts) {
		return pts;
	}

}
