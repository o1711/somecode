#include "TaStage2OrderUpdator.h"


TaLoggerRef TaStage2OrderUpdator::LOG = TaLoggerFactory::getLogger(
		("ta->core->advisor->updator->TaStage2OrderUpdator"));

TaStage2OrderUpdator::TaStage2OrderUpdator(STRING pName, TaOrderExplainer* pExp) :
		TaBaseOrderUpdator(pName, pExp) {

}
TaStage2OrderUpdator::~TaStage2OrderUpdator(){

}
double TaStage2OrderUpdator::getProfitRateForStage1() {
	return TaConstants::OST_UPDATE_ORDER_BINIFIT_RATE_FOR_STAGE1;
}

double TaStage2OrderUpdator::calculateStopLossForStage2() {
return 0.0;
}

double TaStage2OrderUpdator::retriveOriginalRisk(int stage) {
	double stoploss = this->getOrderExplainer()->getOrderStopLoss();
	double takeprofit = this->getOrderExplainer()->getOrderTakeProfit();

	if (stage == 1) {
		return TaLang::__MathAbs(takeprofit - stoploss)
				/ (this->explainer->getOrderOriginalProfitRate() + 1);
	} else {
		return 0; //
	}
}
int TaStage2OrderUpdator::retriveStage() {
	double lots = TaLang::__OrderLots();
	double originalLots = this->explainer->getOrderOriginalLots(); //
	if (lots == originalLots) {
		return 1;
	} else {
		return 2;
	}
}

double TaStage2OrderUpdator::calculateLotsToCloseForStage1() {
	return this->explainer->getOrderOriginalLots() / 2;
}

double TaStage2OrderUpdator::calculateFirstProfitForStage2(double stoploss2) {
return 0.0;
}
//	return open	+ TaConstants::OST_MA2_TAKE_PROFIT_RATE2
//								* (this->getOrderExplainer()->isOrderUp() ?
//										(originalRisk * benifitRate) :
//										(-originalRisk * benifitRate)); //
bool TaStage2OrderUpdator::tryUpdateOrder(TaOrderUpdatingActionRef& uAr) {
	TaOrderUpdatingAction* uA = uAr.o();
	int stage = this->retriveStage();

	if (stage == 0) {
		//exception?
		return false;
	}
	STRING comment = TaLang::__OrderComment();

	double lots = TaLang::__OrderLots();
	double originalRisk = retriveOriginalRisk(stage);

//	doLog( //
//			STRINGConcatenate( //
//					"lots:", TaLang::DoubleTo(lots), //
//					"orderId:", IntegerTo(uA->getOrderId()), //
//					"stage:", IntegerTo(stage), //
//					"originalRisk:", TaLang::DoubleTo(originalRisk)));

	if (stage == 1) { //close first risk of profit got

		//	double
		double benifitRate = getProfitRateForStage1();
		if (this->getOrderExplainer()->isOrderProfitOfRisk(originalRisk,
				benifitRate)) {
			//doLog(STRINGConcatenate("stage1,close1/2"));
			//move stop loss to the open price->
			//move take profit to 5*risk->
			double openPrice = TaLang::__OrderOpenPrice();
			double stoploss2 = openPrice;
			double benefit2 = this->calculateFirstProfitForStage2(stoploss2);
			double takeprofit2 = stoploss2
					+ (this->getOrderExplainer()->isOrderUp() ?
							benefit2 : -benefit2);
//			LOG.o()->info(
//					TaLang::__StringConcatenate(("modify order1:openPrice:"),
//							TaLang::__DoubleToString(openPrice), //
//							("sl2:"), TaLang::__DoubleToString(stoploss2), //
//									("tp2:"), TaLang::__DoubleToString(takeprofit2), //
//											("benefit2:"), TaLang::__DoubleToString(benefit2), //
//													("")));
//
			this->modifyOrder(uA->getOrderId(), openPrice, stoploss2,
					takeprofit2);

			//and close part of lots->
			double lotsToClose = this->calculateLotsToCloseForStage1(); //
			this->closeOrder(uA->getOrderId(), lotsToClose);
		}

	}

	if (stage == 2) { //last 1/3

		double astoploss2 = this->calculateStopLossForStage2();

		double astopLoss = this->getOrderExplainer()->getOrderStopLoss();

		double adelta = astoploss2 - astopLoss;
		if (this->getOrderExplainer()->getOrderDirection().o()->multiple(adelta)
		> 0.00005) {
			//doLog(STRINGConcatenate("stage2,modify for ma increasing->"));

			double atakeprofit = this->getOrderExplainer()->getOrderTakeProfit();
			double atakeprofit2 = atakeprofit + adelta; //

			double aopenPrice = TaLang::__OrderOpenPrice();
			LOG.o()->info(("modify order2"));
			this->modifyOrder(uA->getOrderId(), aopenPrice, astoploss2,
					atakeprofit2);

		}

	}
	return false;
}

