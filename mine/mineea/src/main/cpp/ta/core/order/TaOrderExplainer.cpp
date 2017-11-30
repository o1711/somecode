#include "TaOrderExplainer.h"

TaOrderExplainer::TaOrderExplainer() :
		TaContainerAwareObject() {

}

TaOrderExplainer::~TaOrderExplainer() {

}

double TaOrderExplainer::getOrderOriginalProfitRate(){
	return 0.0;
}

double TaOrderExplainer::getOrderOriginalRisk() {
	double stoploss = this->getOrderStopLoss();
	double takeprofit = this->getOrderTakeProfit();
	return TaLang::__MathAbs(takeprofit - stoploss) / (getOrderOriginalProfitRate() + 1);
}

double TaOrderExplainer::getOrderOriginalLots() {
	return 0.0;
}

double TaOrderExplainer::getOrderProfitForPrice(double price) {
	double openPrice = TaLang::__OrderOpenPrice();
	if (this->isOrderUp()) {
		return price - openPrice;
	} else {
		return openPrice - price;
	}
}
const TaVDirRef TaOrderExplainer::getOrderDirection() {
	int orderType = TaLang::__OrderType();
	if (orderType == TaLang::OP_BUY_) {
		return TaConstants::DIR_UP;
	} else if (orderType == TaLang::OP_SELL_) {
		return TaConstants::DIR_DOWN;
	}
	return NULL;
}
bool TaOrderExplainer::isOrderUp() {
	return TaLang::__OrderType() == TaLang::OP_BUY_;
}

double TaOrderExplainer::getOrderOpenPrice() {
	return TaLang::__OrderOpenPrice();
}

double TaOrderExplainer::currentPrice() {

	return this->isOrderUp() ? TaLang::getBid() : TaLang::getAsk();
}

double TaOrderExplainer::getOrderStopLoss() {
	return TaLang::__OrderStopLoss();
}

double TaOrderExplainer::getOrderTakeProfit() {
	return TaLang::__OrderTakeProfit();
}

bool TaOrderExplainer::isOrderProfitOfRisk(double originalRisk,double factor) {
	double currentPrice = currentPrice();
	double stoploss = TaLang::__OrderStopLoss();
	double openPrice = TaLang::__OrderOpenPrice();
	double riskFactor = originalRisk * factor;
//	//this->doLog(
//			STRINGConcatenate(
//					//
//					",factor:", TaLang::DoubleTo(factor), //
//					"stoploss:", TaLang::DoubleTo(stoploss), //
//					",risk:", TaLang::DoubleTo(originalRisk), //
//					",currentPrice:", TaLang::DoubleTo(currentPrice), //
//					"stoploss + originalRisk + riskFactor:",
//					TaLang::DoubleTo(stoploss + originalRisk + riskFactor), //
//					""));
	////this->doLog(STRINGConcatenate("currentPrice",TaLang::DoubleTo(currentPrice),"risk:",TaLang::DoubleTo(risk),",riskFactor:",TaLang::DoubleTo(riskFactor)));
	if (this->isOrderUp()) {
		return currentPrice >= stoploss + originalRisk + riskFactor;
	} else {
		return currentPrice <= stoploss - originalRisk - riskFactor;
	}
}

bool TaOrderExplainer::isOrderProfitHalfRisk(double originalRisk) {
	return isOrderProfitOfRisk(originalRisk,1.0 / 2.0);
}
bool TaOrderExplainer::isOrderProfitOneRisk(double originalRisk) {
	return isOrderProfitOfRisk(originalRisk,1.0);
}
