#include "TaMa2OrderExplainer.h"


TaMa2OrderExplainer::TaMa2OrderExplainer() :
		TaStagesOrderExplainer() {
}

double TaMa2OrderExplainer::getOrderStepLots(){
	return 0.01;
}

TaMa2OrderExplainer::~TaMa2OrderExplainer() {

}

void TaMa2OrderExplainer::setOrderOriginalLots(double pOrderOriginalLots){
	this->orderOriginalLots = pOrderOriginalLots;
}

double TaMa2OrderExplainer::getOrderOriginalLots(){
	return this->orderOriginalLots;
}


double TaMa2OrderExplainer::getOrderOriginalProfitRate(){
	return 3.0;
}
