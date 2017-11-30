#include "TaOrderSender.h"

TaOrderSender::TaOrderSender() :
		TaContainerAwareObject() {

}
TaOrderSender::~TaOrderSender(){

}

void TaOrderSender::setContainer(TaContainerRef& cref){
	this->ref_riskControl =	cref.o()->getComponent(TaConstants::COM_TOTAL_LOSS_WEEKLY_RISK_CONTROL);
	this->riskControl = this->ref_riskControl.obj();

}
bool TaOrderSender::trySendOrder(TaOrderOpeningAction* oA) {
	if(!this->riskControl->isOrderOpenAllowed(oA)){
		//this->doLog("riskControl not allow you to open order:",oA->to());
		return false;
	}
	return this->doSendOrder(oA);

}

bool TaOrderSender::doSendOrder(TaOrderOpeningAction* oA) {
	STRING symbol =TaLang::__Symbol();
	int cmd = oA->getOrderCmd();
	double lots = oA->getOrderLots();
	double price = oA->getOrderPrice();
	double stoploss = oA->getOrderStoploss();
	double takeprofit = oA->getOrderTakeprofit();
	stoploss = TaLang::__NormalizeDouble(stoploss, TaLang::getDigits());
	takeprofit = TaLang::__NormalizeDouble(takeprofit, TaLang::getDigits());
	TaOrderComments* ocs = oA->getOrderComments();
	STRING comments = ocs->to();
	int magic = oA->getOrderMagic();
	TaColorRef clr;

	int orderId = TaLang::__OrderSend(symbol, cmd, lots, price, 3, stoploss, takeprofit,
				comments, magic, 0, clr);

	//this->doLog(
	//		STRINGConcatenate("cmd:", IntegerTo(cmd), ",price:",
	//				DoubleToStr(price), "stoploss:", DoubleToStr(stoploss),
	//				",takeprofit:", DoubleToStr(takeprofit)));
	if (orderId < 0) {
//		this->chartContext->getNotifier()->send(("order sending failed"),
//					IntegerTo(GetLastError()));
//
		return false;
	} else {
		//TODO
//		this->chartContext->getNotifier()->send("order sent,id:",
//					IntegerTo(orderId));
		return true;
	}
}
