#ifndef TaMa2OrderUpdator2_MQH
#define TaMa2OrderUpdator2_MQH

#include "../../core/advisor/updator/TaStagesOrderUpdator.h"
#include "TaMa2OrderExplainer.h"
#include "../../core/advisor/profit/TaDayAtrBasedTakeProfitCalculator.h"

class TaMa2OrderUpdator2: public TaStagesOrderUpdator {

public:
	TaMa2OrderUpdator2(STRING pName,TaStagesOrderExplainer* pExplainer);
	virtual ~TaMa2OrderUpdator2();

protected:

	TaTwoMovingAverageTimeBar* twoMa;

	TaOrderExplainer* getOrderExplainer();
	virtual double getOrderOriginalLots();
};
#endif
