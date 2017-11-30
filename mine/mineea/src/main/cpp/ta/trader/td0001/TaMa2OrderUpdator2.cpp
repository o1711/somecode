#include "TaMa2OrderUpdator2.h"

TaMa2OrderUpdator2::TaMa2OrderUpdator2(STRING pName,TaStagesOrderExplainer* pExplainer) :
		TaStagesOrderUpdator(pName, pExplainer) {

	this->twoMa = new TaTwoMovingAverageTimeBar(20, 100);
}

TaMa2OrderUpdator2::~TaMa2OrderUpdator2(){

}
TaOrderExplainer* TaMa2OrderUpdator2::getOrderExplainer(){
	return this->explainer;
}

