#include "TaDayAtrBasedTakeProfitCalculator.h"


TaDayAtrBasedTakeProfitCalculator::TaDayAtrBasedTakeProfitCalculator():TaTakeProfitCalculator(){

}

TaDayAtrBasedTakeProfitCalculator::~TaDayAtrBasedTakeProfitCalculator(){

}

double TaDayAtrBasedTakeProfitCalculator::calculate(TaOrderUpdatingActionRef& uAr){
	double atr = TaLang::__iATR(NULL,TaLang::PERIOD_D1_,100,0);

	return atr;

}
