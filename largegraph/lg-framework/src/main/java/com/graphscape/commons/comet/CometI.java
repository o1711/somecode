/**
 *  
 */
package com.graphscape.commons.comet;

import com.graphscape.commons.lang.HasAttributeI;


/**
 * @author wu
 * 
 */
public interface CometI extends HasAttributeI{
	
	public String getProtocol();
	
	public String getId();
	
	public String getCredential();

	public void sendMessage(Object msg);

	public void addListener(CometListenerI ln);

}
