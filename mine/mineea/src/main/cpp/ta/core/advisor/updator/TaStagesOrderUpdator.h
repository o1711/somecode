#ifndef TaStagesOrderUpdator_MQH
#define TaStagesOrderUpdator_MQH

#include "../support/TaBaseOrderUpdator.h"
#include "../../order/TaStagesOrderExplainer.h"
#include "../TaTakeProfitCalculator.h"
#include "..//updator/TaOrderUpdatingAction.h"
/**
 * Performance to be improved->
 */
class TaStagesOrderUpdator: public TaBaseOrderUpdator {
public:
	static TaLoggerRef LOG;
public:
	TaStagesOrderUpdator(STRING pName, TaStagesOrderExplainer* pExplainer);
	virtual ~TaStagesOrderUpdator();

	virtual bool tryUpdateOrder(TaOrderUpdatingActionRef& uAr);
	virtual double calculateProfitRateForStage(int stage);
	virtual double calculateOrderStopLossForStage(int stage, double openPrice);
	virtual double calculateOrderTakeProfitForStage(int stage,
			double openPrice);
protected:
	TaStagesOrderExplainer* explainer;
};
#endif
