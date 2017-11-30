#include "TaStagesOrderUpdator.h"

TaLoggerRef TaStagesOrderUpdator::LOG = TaLoggerFactory::getLogger("ta->core->updator->TaStagesOrderUpdator");

TaStagesOrderUpdator::TaStagesOrderUpdator(STRING pName, TaStagesOrderExplainer* pExplainer) :
		TaBaseOrderUpdator(pName, pExplainer) {
	this->explainer = pExplainer;
}
TaStagesOrderUpdator::~TaStagesOrderUpdator(){

}
double TaStagesOrderUpdator::calculateOrderStopLossForStage(int stage,
		double openPrice) {
	double risk = this->explainer->getOrderOriginalRisk();
	double benefitRate = this->calculateProfitRateForStage(stage);
	double benefit = risk * (benefitRate - 2); //
	benefit = (this->getOrderExplainer()->isOrderUp() ? +benefit : -benefit);

	double rt = openPrice + benefit;
//	LOG.o()->trace(
//			TaLang::__StringConcatenate("openPrice:", TaLang::DoubleTo(openPrice),
//					"benefit:", TaLang::DoubleTo(benefit), "risk:",
//					TaLang::DoubleTo(risk), "rt:", TaLang::DoubleTo(rt)));
	return rt;
}
double TaStagesOrderUpdator::calculateOrderTakeProfitForStage(int stage,
		double openPrice) {
	double risk = this->explainer->getOrderOriginalRisk();
	double benefitRate = this->calculateProfitRateForStage(stage);
	double benefit = risk * (benefitRate + 3); //
	return openPrice + (this->getOrderExplainer()->isOrderUp() ? benefit : -benefit);
}

double TaStagesOrderUpdator::calculateProfitRateForStage(int stage) {
	return stage;
}

bool TaStagesOrderUpdator::tryUpdateOrder(TaOrderUpdatingActionRef& uAr) {
	TaOrderUpdatingAction* uA = uAr.o();
	int totalStages = this->explainer->getOrderTotalStage();
	int stage = this->explainer->getOrderCurrentStage();

	if (stage == 0) {
		//exception?
		return false;
	}

	STRING comment = TaLang::__OrderComment();

	double lots = TaLang::__OrderLots();

	double originalRisk = this->explainer->getOrderOriginalRisk();

//	doLog( //
//			STRINGConcatenate( //
//					"lots:", TaLang::DoubleTo(lots), //
//					"orderId:", IntegerTo(uA->getOrderId()), //
//					"stage:", IntegerTo(stage), //
//					"originalRisk:", TaLang::DoubleTo(originalRisk)));

//	double
	double profitRate = this->calculateProfitRateForStage(stage);
	if (this->getOrderExplainer()->isOrderProfitOfRisk(originalRisk,
			profitRate)) {
		//doLog(STRINGConcatenate("stage1,close1/2"));
		//move stop loss to the open price->
		//move take profit to 5*risk->
		double openPrice = TaLang::__OrderOpenPrice();
		double stoploss2 = this->calculateOrderStopLossForStage(stage + 1,
				openPrice);
		double takeprofit2 = this->calculateOrderTakeProfitForStage(stage + 1,
				openPrice);
//		LOG.o()->trace(
//				STRINGConcatenate("modify order:",uA->getOrderId(),",ask:", TaLang::DoubleTo(Ask),"Bid:", TaLang::DoubleTo(Bid),"stoploss:", TaLang::DoubleTo(stoploss2),
//						"takeprofit:", TaLang::DoubleTo(takeprofit2)));
		this->modifyOrder(uA->getOrderId(), openPrice, stoploss2, takeprofit2);


		double closeLots = this->explainer->getOrderStepLots();
		//LOG.o()->trace("closeOrder:"+uA->getOrderId());
		//and close part of lots->
		this->closeOrder(uA->getOrderId(), closeLots);
	}

	return false;
}

