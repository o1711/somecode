/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 27, 2012
 */
package com.fs.expector.gridservice.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.support.SPISupport;
import com.fs.gridservice.commons.api.EventDispatcherI;
import com.fs.gridservice.commons.api.GlobalEventDispatcherI;
import com.fs.gridservice.commons.api.GridFacadeI;
import com.fs.gridservice.commons.api.terminal.TerminalManagerI;
import com.fs.webserver.api.WebAppI;
import com.fs.webserver.api.WebServerI;

/**
 * @author wu
 * 
 */
public class ExpectorGsSPI extends SPISupport {

	/**
	 * @param id
	 */
	public ExpectorGsSPI(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/* */
	@Override
	public void doActive(ActiveContext ac) {
		ac.active("authProvider");
		// Converter factory
		ac.active("onlineNotifyService");
		// this.activeConfirmCodeNotifier(ac);
		this.activeHandlers(ac);
		GridFacadeI gf = ac.getContainer().find(GridFacadeI.class, true);
		TerminalManagerI tm = gf.getEntityManager(TerminalManagerI.class);

		// the interceptor for messages from/to terminal
		tm.addBeforeMessageSendingHandler(new ErrorInfoLocalizerTMSH());

		// the servlet for image url
		WebServerI ws = ac.getContainer().find(WebServerI.class, true);
		WebAppI gs = ws.addWebApp(ac, "gs", this.getId() + ".WebApp.GS");

		gs.addServlet(ac, "imageUrlServlet",
				Configuration.properties(this.id + ".servletHolders.imageUrlServlet"));

	}

	protected void activeHandlers(ActiveContext ac) {
		EventDispatcherI ged = ac.getContainer().find(GlobalEventDispatcherI.class, true);

		Configuration cfg = Configuration.properties(this.id + ".globalEventDispatcher");

		ged.getEngine().getDispatcher()
				.addHandlers(cfg.getId(), cfg.getPropertyAsCsv("handlers").toArray(new String[] {}));

	}

	//
	// protected void activeConfirmCodeNotifier(ActiveContext ac) {
	//
	// ConfigFactoryI cf = ac.getContainer().find(ConfigFactoryI.class, true);
	//
	// PopulatorI pp = cf.newPopulator().container(ac.getContainer())
	// .type("confirmCodeNotifier");
	// pp.spi(this).active(ac).force(true).populate();
	//
	// }

	/*
	 * Apr 6, 2013
	 */
	@Override
	protected void doBeforeShutdown(int loop) {
		//

	}

}
