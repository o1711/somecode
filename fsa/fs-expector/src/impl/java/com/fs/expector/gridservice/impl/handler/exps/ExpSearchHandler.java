/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.exps;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeSearchOperationI;
import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpSearchHandler extends ExpectorTMREHSupport {

	private int defaultSearchSlop = 3;

	/*
	 * Apr 3, 2013
	 */
	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		this.defaultSearchSlop = cfg.getPropertyAsInt("defaultSearchSlop", 3);

	}

	// query the connected exp from specified exp by exp id.
	@Handle("connected")
	public void handleConnected(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		String expId1 = req.getString("expId1", true);
		NodeSearchOperationI<Connection> qo = this.dataService.prepareNodeSearch(Connection.class);
		qo.propertyEq(Connection.EXP_ID1, expId1);
		NodeSearchResultI<Connection> rst = qo.execute().getResult().assertNoError();

		List<Expectation> expL = new ArrayList<Expectation>();
		for (Connection c : rst.list()) {
			String id = c.getExpId2();
			Expectation exp = this.dataService.getNewestById(Expectation.class, id, true);
			expL.add(exp);
		}
		this.processExpsResult(res, expL);

	}

	@Handle("search")
	public void handleSearch(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//

		Integer from = (Integer) req.getPayload("firstResult", true);
		Integer max = (Integer) req.getPayload("maxResult", true);
		boolean includeMe = req.getBoolean("includeMine", true);
		int slop = (Integer) req.getPayload("slop", this.defaultSearchSlop);

		String expId = (String) req.getPayload("expId");// may null

		String phrase = (String) req.getPayload("phrase");// may null

		NodeSearchOperationI<Expectation> qo = this.dataService.prepareNodeSearch(Expectation.class);

		qo.first(from);
		qo.maxSize(max);

		boolean isOpen = true;
		if (isOpen) {
			qo.propertyEq(Expectation.STATUS, Expectation.ST_OPEN);
		}

		if (!includeMe) {
			String thisAccId = this.getAccountId(ew, true);//
			qo.propertyNotEq(Expectation.ACCOUNT_ID, thisAccId);
		}

		if (phrase != null) {
			qo.multiMatch(new String[] { Expectation.TITLE, Expectation.BODY }, phrase, slop);
		}

		// qo.propertyMatch(Expectation.BODY, phrase, slop);
		qo.sort(NodeI.PK_TIMESTAMP, true);
		NodeSearchResultI<Expectation> rst = qo.execute().getResult().assertNoError();
		this.processExpsResult(res, rst.list());

	}

	private void processExpsResult(ResponseI res, List<Expectation> list) {
		List<PropertiesI<Object>> el = this.efacade.convertToClientFormat(list);

		res.setPayload("expectations", el);
	}

	@Handle("get")
	public void handleGet(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		String expId = req.getString("expId", true);
		Expectation exp = this.dataService.getNewestById(Expectation.class, expId, true);
		PropertiesI<Object> pts = exp.getTarget();
		this.efacade.processExpIcon(pts);
		res.setPayload("expectation", pts);// good
	}

}