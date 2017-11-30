/**
 * Jun 12, 2012
 */
package com.graphscape.gwt.core;

import com.graphscape.gwt.core.CodecI;
import com.graphscape.gwt.core.RootI;
import com.graphscape.gwt.core.commons.ProtocolPort;
import com.graphscape.gwt.core.core.UiObjectI;
import com.graphscape.gwt.core.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public interface ClientI extends UiObjectI {

	public String getClientId();

	public EndPointI getEndpoint(boolean force);

	public RootI getRoot();

	public void setParameter(String key, String value);

	public String getParameter(String key, String def);

	public String getParameter(String key, boolean force);
	
	public int getParameterAsInt(String key, int def);
	
	public boolean getParameterAsBoolean(String key, boolean def);

	public CodecI.FactoryI getCodecFactory();

	public String getPreferedLocale();

	public String localized(String key);

	public void setProtocolPort(ProtocolPort pp);
	
	public void start();//

}
