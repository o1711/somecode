/**
 * Dec 14, 2013
 */
package com.graphscape.commons.comet.spi;

import com.graphscape.commons.comet.CometListenerI;
import com.graphscape.commons.session.SessionManagerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface CometServiceProviderI {

	public SessionManagerI getSessionManager();

	public CometListenerI getCometListener();
	
	public CometAuthProviderI getAuthProvider();
	
}
