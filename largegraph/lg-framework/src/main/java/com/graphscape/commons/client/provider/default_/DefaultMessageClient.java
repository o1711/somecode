/**
 * 
 */
package com.graphscape.commons.client.provider.default_;

import com.graphscape.commons.client.MessageClientServiceProviderI;
import com.graphscape.commons.client.provider.default_.ajax.AjaxMessageClient;

/**
 * @author wuzhen
 * 
 */
public class DefaultMessageClient extends AjaxMessageClient {

	/**
	 * @param name
	 * @param mcspi
	 */

	public DefaultMessageClient(MessageClientServiceProviderI mcspi) {
		super(mcspi);
	}

}
