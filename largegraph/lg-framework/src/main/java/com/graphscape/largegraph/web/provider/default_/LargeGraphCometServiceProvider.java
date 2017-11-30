/**
 * Dec 14, 2013
 */
package com.graphscape.largegraph.web.provider.default_;

import com.graphscape.commons.comet.CometListenerI;
import com.graphscape.commons.comet.spi.CometAuthProviderI;
import com.graphscape.commons.comet.spi.CometServiceProviderI;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.message.MessageI;
import com.graphscape.commons.session.SessionManagerI;
import com.graphscape.largegraph.server.LargeGraphServerI;
import com.graphscape.largegraph.server.MessageQueueManagerI;

/**
 * Statefull message processing.
 * 
 * @author wuzhen0808@gmail.com
 * 
 */
public class LargeGraphCometServiceProvider implements CometServiceProviderI {

	private CallbackI<MessageI, MessageI> callback;
	private SessionManagerI sessionManager;
	private LargeGraphServerI lgs;
	MessageQueueManagerI messageQueueManager;
	CometAuthProviderI authProvider;

	public LargeGraphCometServiceProvider(LargeGraphServerI lgs, MessageQueueManagerI mqm,
			SessionManagerI sm, CallbackI<MessageI, MessageI> cb,CometAuthProviderI ap) {
		this.callback = cb;
		this.messageQueueManager = mqm;
		this.lgs = lgs;
		this.sessionManager = sm;
		this.authProvider = ap;
	}

	@Override
	public SessionManagerI getSessionManager() {
		return this.sessionManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.comet.spi.CometServiceProviderI#getCometListener()
	 */
	@Override
	public CometListenerI getCometListener() {

		return new LargeGraphCometListener(lgs, this.messageQueueManager);
	}

	@Override
	public CometAuthProviderI getAuthProvider() {

		return this.authProvider;
	}

}
