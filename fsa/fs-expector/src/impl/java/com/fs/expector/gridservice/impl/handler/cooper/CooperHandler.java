/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.cooper;

import org.json.simple.JSONArray;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.dataservice.api.wrapper.ConnectRequest;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.expector.gridservice.impl.handler.expm.ExpMessageHandler;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class CooperHandler extends ExpectorTMREHSupport {

	public static final String MP_CONNECT_REQUEST = "/connect/request";

	public static final String MP_TEXT_MESSAGE = "/text-message";

	private CodecI codec;

	// query activities by account.

	@Handle("request")
	public void handleRequest(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {

		MessageI req = ew.getMessage();//

		String aid = this.getSession(ew, true).getAccountId();//
		// the relation between activity and user.
		String expId1 = (String) req.getPayload("expId1", true);
		String expId2 = (String) req.getPayload("expId2", true);

		// check if the max connect overflow

		if (this.efacade.getOverflowConnectedExpCount(expId1) >= 0) {
			//
			res.getErrorInfos().addError("connection.overflow", "source exp connected too much");
			return;
		}

		// check if the target exp connect overflow
		if (this.efacade.getOverflowConnectedExpCount(expId2) >= 0) {
			//
			res.getErrorInfos().addError("connection.overflow", "target exp connected too much");
			return;
		}

		Expectation exp2 = this.dataService.getNewestById(Expectation.class, expId2, true);
		String accId2 = exp2.getAccountId();
		ConnectRequest cr = new ConnectRequest().forCreate(this.dataService);
		cr.setAccountId1(aid);
		cr.setExpId1(expId1);
		cr.setExpId2(expId2);
		cr.setAccountId2(accId2);
		cr.save(true);

		String cid = cr.getId();
		//

		res.setPayload("cooperRequestId", cid);//
		// notify the exp2's account to refresh the incoming request,if he/she
		// is online

		// TODO move below code to a service: ExpMessageServiceI?
		ExpMessage em = new ExpMessage().forCreate(this.dataService);
		em.setExpId1(expId1);
		em.setExpId2(expId2);
		em.setAccountId1(aid);
		em.setAccountId2(accId2);
		em.setPath(MP_CONNECT_REQUEST);
		em.setHeader("");
		PropertiesI<Object> pts = new MapProperties<Object>();
		pts.setProperty("cooperRequestId", cid);
		String body = this.encodeMessageBody(pts);
		em.setBody(body);
		em.save(true);

		MessageI msg = new MessageSupport("/notify/exp-message-created");
		msg.setHeader("expId1", expId1);
		msg.setHeader("expId2", expId2);

		this.onlineNotifyService.tryNotifyAccount(accId2, msg);
	}

	private String encodeMessageBody(PropertiesI<Object> pts) {

		JSONArray jsn = (JSONArray) this.codec.encode(pts);
		return jsn.toJSONString();
	}

	@Handle("get")
	public void handleGet(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {
		MessageI req = ew.getMessage();//
		String crId = (String) req.getPayload("cooperRequestId", true);
		ConnectRequest cr = this.dataService.getNewestById(ConnectRequest.class, crId, false);
		if (cr != null) {
			res.setPayload("cooperRequest", cr.getTarget());
		} else {
			res.setPayload("cooperRequest", null);
		}

	}

	@Handle("reject")
	public void handleReject(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {
		MessageI req = ew.getMessage();//
		String crid = (String) req.getPayload("cooperRequestId", true);
		ConnectRequest cr = this.dataService.getNewestById(ConnectRequest.class, crid, true);
		String accId1 = cr.getAccountId1();
		String expId1 = cr.getExpId1();
		String accId2 = cr.getAccountId2();// todo check permission
		String expId2 = cr.getExpId2();

		this.dataService.deleteById(ConnectRequest.class, crid);// delete this
		// cr,if

		res.setPayload("crId", crid);//

		String text = "Cooperation request is rejected.";
		ExpMessageHandler.createTextMessage(this.dataService, codec, expId2, expId1, text);
		this.onlineNotifyService.tryNotifyExpMessageCreated(accId1, expId2, expId1);
	}

	@Handle("confirm")
	public void handleConfirm(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {
		MessageI req = ew.getMessage();//
		String crid = (String) req.getPayload("cooperRequestId", true);
		ConnectRequest cr = this.dataService.getNewestById(ConnectRequest.class, crid, true);
		String accId1 = cr.getAccountId1();
		String accId2 = cr.getAccountId2();
		String expId1 = cr.getExpId1();
		String expId2 = cr.getExpId2();
		// from one to another
		Connection c = new Connection().forCreate(this.dataService);
		c.setAccountId1(cr.getAccountId1());
		c.setAccountId2(cr.getAccountId2());
		c.setExpId1(cr.getExpId1());
		c.setExpId2(cr.getExpId2());
		c.save(true);

		// the reverse connect
		c = new Connection().forCreate(this.dataService);
		c.setAccountId1(cr.getAccountId2());
		c.setAccountId2(cr.getAccountId1());
		c.setExpId1(cr.getExpId2());
		c.setExpId2(cr.getExpId1());
		c.save(true);

		this.dataService.deleteById(ConnectRequest.class, crid);// delete this
																// cr,if

		res.setPayload("crId", crid);//
		{
			MessageI msg = new MessageSupport("/notify/exp-connect-created");
			msg.setPayload("expId", expId1);
			this.onlineNotifyService.tryNotifyAccount(accId1, msg);
		}
		{
			MessageI msg = new MessageSupport("/notify/exp-connect-created");
			msg.setPayload("expId", expId2);

			this.onlineNotifyService.tryNotifyAccount(accId2, msg);

		}

	}

	// @Handle("incomingCr")
	// public void handleRefreshIncomingCr(TerminalMsgReceiveEW ew, ResponseI
	// res) {
	//
	// String accId = this.getAccountId(ew, true);
	//
	// List<ConnectRequest> crL =
	// this.dataService.getListNewestFirst(ConnectRequest.class,
	// ConnectRequest.ACCOUNT_ID2, accId, 0, Integer.MAX_VALUE);
	// List<PropertiesI<Object>> ptsL = NodeWrapperUtil.convert(crL);
	// res.setPayload("cooperRequestList", ptsL);
	// }

	/*
	 * Mar 9, 2013
	 */
	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);

		this.codec = this.getContainer().getTop().find(CodecI.FactoryI.class, true)
				.getCodec(PropertiesI.class);
	}

}
