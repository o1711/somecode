/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 3, 2012
 */
package com.fs.commons.api.message;

import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;

/**
 * @author wuzhen
 * 
 */
public interface MessageI {

	public static final String HK_PATH = "_path";

	public static final String HK_ID = "_id";

	public static final String HK_SOURCE_ID = "_source_id";

	public static final String HK_SILENCE = "_silence";

	public static final String HK_ERROR_PROCESSOR = "_eprocessor";

	// when this message is processed, the result send to this address.
	public static final String HK_RESPONSE_ADDRESS = "_resonse_address";

	public static final String PK_DEFAULT = "_default";

	public static final String PK_ERROR_INFO_S = "_ERROR_INFO_S";

	public ErrorInfos getErrorInfos();

	public void assertNoError();

	public String getErrorProcessor();

	public String getSourceId();

	public Path getPath();

	public String getId();

	public String getResponseAddress();

	public boolean isSilence();

	public PropertiesI<String> getHeaders();

	public PropertiesI<Object> getPayloads();

	public String getHeader(String key);

	public String getHeader(String key, boolean force);

	public String getHeader(String key, String def);

	public void setHeader(String key, String value);

	public void setHeaders(PropertiesI<String> hds);

	public Object getPayload();

	public Object getPayload(String key);

	public Object getPayload(String key, boolean force);

	public Object getPayload(String key, Object def);

	public <T> T getPayload(Class<T> cls, String key, T def);

	public String getString(String key);

	public String getString(String key, boolean force);

	public String getString(String key, String def);

	public boolean getBoolean(String key, boolean def);

	public void setPayload(String key, Object value);

	public void setPayload(Object pl);

	public void setPayloads(PropertiesI<Object> pls);

	public void setMessage(MessageI msg);

}
