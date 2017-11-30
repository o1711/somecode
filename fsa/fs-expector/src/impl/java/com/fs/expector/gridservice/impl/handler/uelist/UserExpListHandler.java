/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.expector.gridservice.impl.handler.uelist;

import java.util.List;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeSearchOperationI;
import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.wrapper.ConnectRequest;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class UserExpListHandler extends ExpectorTMREHSupport {
	@Handle("init")
	public void handleInit(MessageContext hc, ResponseI res) {

	}

	/**
	 * get the detail of exp: the activity related.
	 * <p>
	 * Nov 28, 2012
	 */
	@Handle("get")
	public void handleGet(MessageContext hc, MessageI req, ResponseI res) {
		String expId = (String) req.getPayload("expId");
		Expectation exp = this.dataService.getNewestById(Expectation.class, expId, false);

		// TODO add loginId,add interceptor?

	}

	/**
	 * Refresh the summary list of ue. Nov 28, 2012
	 */
	@Handle("refresh")
	public void handleRefresh(TerminalMsgReceiveEW ew, MessageContext hc, ResponseI res) {
		SessionGd login = this.getSession(ew, true);
		MessageI req = ew.getMessage();//
		String status = req.getString("status");

		NodeSearchOperationI<Expectation> finder = this.dataService.prepareNodeSearch(Expectation.class);

		// finder.addBeforeInterceptor(null);//TODO
		if (status != null) {
			finder.propertyEq(Expectation.STATUS, status);
		}
		finder.propertyEq(Expectation.ACCOUNT_ID, login.getAccountId());

		NodeSearchResultI<Expectation> rst = finder.execute().getResult().assertNoError();

		List<PropertiesI<Object>> el = this.efacade.convertToClientFormat(rst.list());

		res.setPayload("userExpList", el);
	}

	// TODO how to process the already confirmed request.
	protected List<PropertiesI<Object>> getCooperRequestList(String expId2) {
		// TODO query the count of request
		NodeSearchOperationI<ConnectRequest> crq = this.dataService.prepareNodeSearch(ConnectRequest.class);
		// crq.first(0);
		// crq.maxSize(10);//TODO limit from
		crq.propertyEq(ConnectRequest.EXP_ID2, expId2);//
		NodeSearchResultI<ConnectRequest> rst = crq.execute().getResult().assertNoError();
		List<PropertiesI<Object>> rt = NodeWrapperUtil.convert(rst.list());
		return rt;

	}
}
