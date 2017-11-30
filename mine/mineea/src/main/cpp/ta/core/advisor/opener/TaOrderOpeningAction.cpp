#include "TaOrderOpeningAction.h"

TaOrderOpeningAction::TaOrderOpeningAction() {
	this->orderComments = new TaOrderComments();
}

TaOrderOpeningAction::~TaOrderOpeningAction() {
	delete this->orderComments;
}

void TaOrderOpeningAction::setOrderCmd(int pOrderCmd) {
	this->orderCmd = pOrderCmd;
}

void TaOrderOpeningAction::setOrderMagic(int pOrderMagic) {
	this->orderMagic = pOrderMagic;
}

void TaOrderOpeningAction::setOrderLots(double pOrderLots) {
	this->orderLots = pOrderLots;
}

void TaOrderOpeningAction::setOrderRisk(double pOrderRisk) {
	this->orderRisk = pOrderRisk;
}

void TaOrderOpeningAction::setOrderPrice(double pOrderPrice) {
	this->orderPrice = pOrderPrice;
}
void TaOrderOpeningAction::setOrderStoploss(double pOrderStoploss) {
	this->orderStoploss = pOrderStoploss;
}
void TaOrderOpeningAction::setOrderTakeprofit(double pOrderTakeprofit) {
	this->orderTakeprofit = pOrderTakeprofit;
}

int TaOrderOpeningAction::getOrderCmd() {
return this->orderCmd;
}

int TaOrderOpeningAction::getOrderMagic() {
return this->orderMagic;
}

double TaOrderOpeningAction::getOrderLots() {
return this->orderLots;
}
double TaOrderOpeningAction::getOrderRisk() {
return this->orderRisk;
}

double TaOrderOpeningAction::getOrderPrice() {
return this->orderPrice;
}
double TaOrderOpeningAction::getOrderStoploss() {
return this->orderStoploss;
}
double TaOrderOpeningAction::getOrderTakeprofit() {
return this->orderTakeprofit;
}

TaOrderComments* TaOrderOpeningAction::getOrderComments() {
return this->orderComments;
}
STRING TaOrderOpeningAction::to() {
return TaLang::__StringConcatenate(
		TaLang::__StringConcatenate(
				(this->orderCmd == TaLang::OP_BUY_ ? "buy" : "sell"), ",lots:",
				TaLang::__DoubleToString(this->orderLots)), //
		TaLang::__StringConcatenate(",price:",
				TaLang::__DoubleToString(this->orderPrice)), //
		TaLang::__StringConcatenate(",sl:",
				TaLang::__DoubleToString(this->orderStoploss)), //
		TaLang::__StringConcatenate(",tp:",
				TaLang::__DoubleToString(this->orderTakeprofit)), //
		TaLang::__StringConcatenate(",risk:",
				TaLang::__DoubleToString(this->orderRisk)));
}
