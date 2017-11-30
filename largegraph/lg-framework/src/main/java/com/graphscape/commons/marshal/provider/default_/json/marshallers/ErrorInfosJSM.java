/**
 * Jul 17, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json.marshallers;

import java.util.List;

import com.google.gson.JsonArray;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.json.ListJsonMarshallerSupport;

/**
 * @author wu
 * 
 */
public class ErrorInfosJSM extends ListJsonMarshallerSupport<ErrorInfos> implements
		MarshallerI<JsonArray> {
	/** */
	public ErrorInfosJSM(MarshallingProviderI f) {
		super("ES", ErrorInfos.class, f);

	}

	/* */
	@Override
	protected ErrorInfos convert(List pts) {
		ErrorInfos rt = new ErrorInfos();
		rt.getErrorInfoList().addAll(pts);
		return rt;

	}

	/* */
	@Override
	protected List convert(ErrorInfos t) {
		List rt = t.getErrorInfoList();
		return rt;

	}

}
