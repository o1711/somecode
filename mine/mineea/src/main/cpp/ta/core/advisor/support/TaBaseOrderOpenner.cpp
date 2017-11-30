#include "TaBaseOrderOpenner.h"


TaBaseOrderOpenner::TaBaseOrderOpenner(int pStrategy,STRING pName, const TaVDirRef& dir) :
		TaOrderOpenner(pStrategy,pName) {

	this->takeProfitRate = 1.0;
	this->direction = dir;
	this->maxOrderRisk = TaConstants::OST_OPEN_ORDER_MAX_RISK;
	this->minOrderRisk = TaConstants::OST_OPEN_ORDER_MIN_RISK;

}

TaBaseOrderOpenner::~TaBaseOrderOpenner() {

}
void TaBaseOrderOpenner::setContainer(TaContainerRef& cref){
	this->ref_orderSender =	cref.o()->getComponent(("componentOrderSender"));
	this->orderSender = this->ref_orderSender.obj();

}

void TaBaseOrderOpenner::setTakeProfitRate(double pRate){
	this->takeProfitRate = pRate;
}
double TaBaseOrderOpenner::orderStoploss(TaOrderOpeningAction* oA) {
	double risk = oA->getOrderRisk();
	double price = oA->getOrderPrice();
	if (this->direction.o()->isUp()) {
		risk = -risk;
	}
	return price + risk;
}

double TaBaseOrderOpenner::orderTakeprofit(TaOrderOpeningAction* oA) {
	double risk = oA->getOrderRisk();
	double price = oA->getOrderPrice();
	if (this->direction.o()->isUp()) {
		return price + risk * this->takeProfitRate;
	} else {
		return price - risk * this->takeProfitRate;
	}
}

double TaBaseOrderOpenner::orderMarketPrice(const TaVDirRef& dir) {
	if (dir.o()->isUp()) {
		return TaLang::getAsk();
	} else {
		return TaLang::getBid();
	}
}

int TaBaseOrderOpenner::exclusiveOrderType() {
	return -1;
}

bool TaBaseOrderOpenner::tryOpenOrder(TaOrderOpeningActionRef& oA) {
	return false;
}

void TaBaseOrderOpenner::sendOrder(TaOrderOpeningAction* oA) {
	//this->doLog("sendOrder()");
	double risk =oA->getOrderRisk();
	if (oA->getOrderRisk() > this->maxOrderRisk) {
		//this->doLog(
		//		STRINGConcatenate("orderRisk:",
		//				TaLang::DoubleTo(oA->getOrderRisk()),
		//				" is exceed the maxOrderRisk:",
		//				TaLang::DoubleTo(this->maxOrderRisk)));
		return;
	}
	if (oA->getOrderRisk() < this->minOrderRisk) {
		//this->doLog(
		//		STRINGConcatenate("orderRisk:",
		//				TaLang::DoubleTo(oA->getOrderRisk()),
		//				" is lower the minOrderRisk:",
		//				TaLang::DoubleTo(this->minOrderRisk)));
		return;
	}
	STRING symbol = TaLang::__Symbol();
	int cmd = oA->getOrderCmd();
	double lots = oA->getOrderLots();
	double price = oA->getOrderPrice();
	double stoploss = oA->getOrderStoploss();

	if (stoploss == 0) {
		stoploss = this->orderStoploss(oA);
		oA->setOrderStoploss(stoploss);
	}

	double takeprofit = oA->getOrderTakeprofit();

	if (takeprofit == 0) {
		takeprofit = this->orderTakeprofit(oA);
		oA->setOrderTakeprofit(takeprofit);
	}


	int oppositeOrderType = this->exclusiveOrderType();



	TaOrderComments* ocs = oA->getOrderComments();
	ocs->setOriginalRisk(risk);
	STRING comments = ocs->to();

	if (this->isContainsOrder(symbol, oppositeOrderType)) {
		//this->doLog(
		//		STRINGConcatenate("cannot create opposite order,symbol:",
		//				symbol, ",cmd:", cmd));
		return;
	}
	oA->setOrderMagic(this->strategy);
	this->orderSender->trySendOrder(oA);
}

bool TaBaseOrderOpenner::isContainsOrder(STRING pSymbol, int pOrderType) {
	int total = TaLang::__OrdersTotal();
	for (int i = 0; i < total; i++) {
		if (TaLang::__OrderSelect(i, TaLang::SELECT_BY_POS_, TaLang::MODE_TRADES_) == false) {
			continue;
		}
		if (TaLang::__OrderSymbol() != pSymbol) {
			continue;
		}
		if (TaLang::__OrderType() == pOrderType) {
			return true;
		}
	}
	return false;

}
