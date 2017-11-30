#ifndef TaStage2OrderUpdator_MQH
#define TaStage2OrderUpdator_MQH

#include "../support/TaBaseOrderUpdator.h"
#include "../../order/TaOrderExplainer.h"
#include "../TaTakeProfitCalculator.h"
#include "..//updator/TaOrderUpdatingAction.h"
/**
 * Performance if Good->
 */
class TaStage2OrderUpdator: public TaBaseOrderUpdator {
private:
	static TaLoggerRef LOG;
public:
	TaStage2OrderUpdator(STRING pName, TaOrderExplainer* pExp);
	virtual ~TaStage2OrderUpdator();

	virtual bool tryUpdateOrder(TaOrderUpdatingActionRef& uAr);

	virtual double retriveOriginalRisk(int stage);
	virtual int retriveStage();
	virtual double calculateLotsToCloseForStage1();
	virtual double calculateStopLossForStage2();
	virtual double calculateFirstProfitForStage2(double stoploss2);
	virtual double getProfitRateForStage1();
};
#endif
