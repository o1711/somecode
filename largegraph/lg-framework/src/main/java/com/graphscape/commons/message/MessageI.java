package com.graphscape.commons.message;

import java.util.Map;

import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.HasAttributeI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.util.Path;

/**
 * @author wuzhen
 *         <p>
 * 
 */
public interface MessageI extends HasAttributeI{

	public static final String HK_PATH = "_path";

	public static final String HK_ID = "_id";
	
	public static final String HK_PIDP = "_pidp";
	
	public static final String HK_CREATED = "_created";

	public Path getPath();

	public String getId();

	public long getCreated();

	public PropertiesI<String> getHeaders();// TODO Headers class.

	public PropertiesI<Object> getPayloads();
	
	public Path getParentIdPath();
	
	public Path getIdPath();

	public String getHeader(String key);

	public String getHeader(String key, boolean force);

	public String getHeader(String key, String def);

	public void setHeader(String key, String value);

	public void setHeaders(PropertiesI<String> hds);

	public void setHeaders(Map<String, String> hds);

	public Object getPayload(String key);

	public Object getPayload(String key, boolean force);

	public Object getPayload(String key, Object def);

	public <T> T getPayload(Class<T> cls, String key, T def);

	public String getString(String key);

	public String getString(String key, boolean force);

	public String getString(String key, String def);

	public boolean getBoolean(String key, boolean def);

	public void setPayload(String key, Object value);

	public void setPayloads(PropertiesI<Object> pls);

	public void setMessage(MessageI msg);

	public PropertiesI<Object> getAsFlatProperties();

	public ErrorInfos getErrorInfos();

	public MessageI clone();

	public MessageI assertNoError();

}
