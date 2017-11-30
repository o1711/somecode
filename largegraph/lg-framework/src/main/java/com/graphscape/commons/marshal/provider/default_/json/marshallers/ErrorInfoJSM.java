/**
 * Jul 17, 2012
 */
package com.graphscape.commons.marshal.provider.default_.json.marshallers;

import java.util.List;

import com.google.gson.JsonElement;
import com.graphscape.commons.lang.ErrorInfo;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.provider.default_.json.PropertiesJsonMarshallerSupport;

/**
 * @author wu
 * 
 */
public class ErrorInfoJSM extends PropertiesJsonMarshallerSupport<ErrorInfo> implements
		MarshallerI<JsonElement> {
	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String DETAIL = "detail";

	/** */
	public ErrorInfoJSM(MarshallingProviderI f) {
		super("E", ErrorInfo.class, f);

	}

	/* */
	@Override
	protected ErrorInfo convert(PropertiesI<Object> pts) {
		String id = (String) pts.getProperty(ID);
		String code = (String) pts.getProperty(CODE);
		String msg = (String) pts.getProperty(MESSAGE);
		List<String> detail = (List<String>) pts.getProperty(DETAIL);
		ErrorInfo rt = new ErrorInfo(code, msg, null, id);
		rt.getDetail().addAll(detail);

		return rt;

	}

	/* */
	@Override
	protected PropertiesI<Object> convert(ErrorInfo t) {
		PropertiesI<Object> rt = new DefaultProperties<Object>();
		rt.setProperty(ID, t.getId());
		rt.setProperty(CODE, t.getCode());
		rt.setProperty(MESSAGE, t.getMessage());
		rt.setProperty(DETAIL, t.getDetail());
		return rt;

	}

}
