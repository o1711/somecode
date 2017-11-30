#include "TaBaseOrderUpdator.h"

TaBaseOrderUpdator::TaBaseOrderUpdator(STRING pName, TaOrderExplainer* pExplainer) :
		TaOrderUpdator(pName) {
	this->explainer = pExplainer;
}
TaBaseOrderUpdator::~TaBaseOrderUpdator(){
	delete this->explainer;
}
TaOrderExplainer* TaBaseOrderUpdator::getOrderExplainer(){
	return this->explainer;
}

void TaBaseOrderUpdator::modifyOrder(int orderId, double openPrice,
		double stoploss2, double takeprofit2) {
	//this->doLog(
	//		STRINGConcatenate("orderId:", IntegerTo(orderId),
	//				",stoploss2:", TaLang::DoubleTo(stoploss2), "takeprofit2:",
	//				TaLang::DoubleTo(takeprofit2)));
	bool modified = TaLang::__OrderModify(orderId, openPrice, stoploss2, takeprofit2,
			NULL, TaColorRef::Null());
	if (!modified) {
		//this->doLog("modify order failed,id:", IntegerTo(orderId));
	}
}
void TaBaseOrderUpdator::closeOrder(int orderId) {
	double lotsToClose = TaLang::__OrderLots();
	this->closeOrder(orderId, lotsToClose);
}

void TaBaseOrderUpdator::closeOrder(int orderId, double lotsToClose) {
	double currentPrice = this->orderClosePrice();
	const TaColorRef closeTaColor = this->orderCloseTaColor();
	bool closed = TaLang::__OrderClose(orderId, lotsToClose, currentPrice, 3, TaConstants::COLOR_RED); //

	if (!closed) {
		//this->doLog("close order failed,id:", IntegerTo(orderId));
	}
}

double TaBaseOrderUpdator::orderClosePrice() {
	if (this->getOrderExplainer()->isOrderUp()) {
		return TaLang::getBid();
	} else {
		return TaLang::getAsk();
	}
}

const TaColorRef TaBaseOrderUpdator::orderCloseTaColor() {
	if (this->getOrderExplainer()->isOrderUp()) {
		return TaConstants::COLOR_RED;
	} else {
		return TaConstants::COLOR_GREEN;
	}
}
