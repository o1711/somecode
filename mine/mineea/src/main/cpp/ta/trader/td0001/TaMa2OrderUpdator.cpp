#include "TaMa2OrderUpdator.h"

TaMa2OrderUpdator::TaMa2OrderUpdator(STRING pName, TaOrderExplainer* pExp) :
		TaStage2OrderUpdator(pName, pExp) {
	this->twoMa = new TaTwoMovingAverageTimeBar(20, 100);
}
TaMa2OrderUpdator::~TaMa2OrderUpdator(){
	delete this->twoMa;
}
double TaMa2OrderUpdator::calculateFirstProfitForStage2(double stoploss2){

	double rt = TaLang::__iATR(NULL,TaLang::PERIOD_D1_,10,0);
	rt = rt * 5.0;
	return rt;
}
double TaMa2OrderUpdator::retriveOriginalTakeProfitRate(){
	return TaConstants::OST_MA2_TAKE_PROFIT_RATE;
}

double TaMa2OrderUpdator::retriveOriginalLots(){
	return TaConstants::OST_MA2_ORIGINAL_LOTS;
}

double TaMa2OrderUpdator::calculateStopLossForStage2(){

	return this->twoMa->getMidValue(0);

}
