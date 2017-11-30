#ifndef TaMa2OrderUpdator_MQH
#define TaMa2OrderUpdator_MQH

#include "../../core/advisor/updator/TaStage2OrderUpdator.h"
#include "TaMa2OrderExplainer.h"
#include "../../core/advisor/profit/TaDayAtrBasedTakeProfitCalculator.h"

class TaMa2OrderUpdator: public TaStage2OrderUpdator {

public:
	TaMa2OrderUpdator(STRING pName,  TaOrderExplainer* pExp);
	virtual ~TaMa2OrderUpdator();

protected:
	TaTwoMovingAverageTimeBar* twoMa;
	virtual double calculateStopLossForStage2();
	virtual double calculateFirstProfitForStage2(double stoploss2);
	virtual double retriveOriginalTakeProfitRate();
	virtual double retriveOriginalLots();
};
#endif
