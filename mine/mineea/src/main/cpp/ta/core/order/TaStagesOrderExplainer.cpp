#include "TaStagesOrderExplainer.h"


TaStagesOrderExplainer::TaStagesOrderExplainer() :
		TaOrderExplainer() {
}

TaStagesOrderExplainer::~TaStagesOrderExplainer() {

}
double TaStagesOrderExplainer::getOrderStepLots(){
	return 0.0;
}

int TaStagesOrderExplainer::getOrderTotalStage() {
	double olots = getOrderOriginalLots();
	double slots = this->getOrderStepLots();
	return (olots / slots);
}

int TaStagesOrderExplainer::getOrderCurrentStage() {
	int totalStages = this->getOrderTotalStage();
	double lots = TaLang::__OrderLots();
	return totalStages - (lots / 0.01) + 1;
}
