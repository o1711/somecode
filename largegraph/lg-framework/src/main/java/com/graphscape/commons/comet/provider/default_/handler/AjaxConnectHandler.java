/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.graphscape.commons.comet.provider.default_.handler;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.comet.provider.default_.AjaxComet;
import com.graphscape.commons.comet.provider.default_.AjaxCometHttpRequestHandler;
import com.graphscape.commons.comet.provider.default_.AjaxCometManagerImpl;
import com.graphscape.commons.comet.provider.default_.AjaxMsg;
import com.graphscape.commons.comet.provider.default_.AjaxMsgContext;
import com.graphscape.commons.comet.provider.default_.AjaxMsgHandler;
import com.graphscape.commons.comet.spi.CometServiceProviderI;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;
import com.graphscape.commons.session.SessionI;
import com.graphscape.commons.session.SessionManagerI;

/**
 * @author wu
 * 
 */
public class AjaxConnectHandler extends AjaxMsgHandler {

	public static final Logger LOG = LoggerFactory.getLogger(AjaxConnectHandler.class);

	private CometServiceProviderI spi;

	/**
	 * @param sessionManager
	 * @param manager
	 */

	public AjaxConnectHandler(SessionManagerI sessionMap, AjaxCometManagerImpl manager,
			CometServiceProviderI spi) {
		super(false, sessionMap, manager);
		this.spi = spi;
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		//
		String userAgent = amc.arc.req.getHeader("User-Agent");
		String credential = (String) amc.am.getProperty("credentical", true);
		LOG.info("connect:" + "user-agent:" + userAgent + ",credentical:" + credential);
		PropertiesI<Object> pts = new DefaultProperties<Object>();
		boolean isOk = this.spi.getAuthProvider().authorize(credential, pts);
		if (!isOk) {
			amc.arc.writeError("authFailure", "auth is failure.");
			return;
		}

		// do connection
		String sid = UUID.randomUUID().toString();

		AjaxComet as = new AjaxComet(sid, credential);
		as.setAttributes(pts);//
		SessionI s = this.sessionManager.createSession(sid, amc.arc.timeoutForSession);//

		s.setAttribute(AjaxCometHttpRequestHandler.SK_COMET, as);

		this.manager.onConnect(as);
		// response
		AjaxMsg am2 = new AjaxMsg(AjaxMsg.CONNECT.getSubPath("success"));
		am2.setProperty(AjaxMsg.PK_CONNECT_SESSION_ID, sid);
		amc.arc.write(am2);

	}
}
