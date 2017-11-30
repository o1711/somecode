#include "../opener/TaOrderOpenner.h"
#ifndef TaBaseOrderOpenner_MQH
#define TaBaseOrderOpenner_MQH

#include "../updator/TaOrderUpdator.h"
#include "../../order/TaOrderSender.h"

class TaBaseOrderOpenner: public TaOrderOpenner {

protected:
	TaVDirRef direction;
	double maxOrderRisk;
	double minOrderRisk;
	double takeProfitRate;
	TaOrderSender* orderSender;
	TaRef ref_orderSender;
public:
	TaBaseOrderOpenner(int strategy,STRING pName, const TaVDirRef& dir);
	virtual ~TaBaseOrderOpenner();
	void setTakeProfitRate(double pRate);
	virtual bool tryOpenOrder(TaOrderOpeningActionRef& oA);
	virtual void setContainer(TaContainerRef& cref);

protected:

	virtual void sendOrder(TaOrderOpeningAction* oA);
	virtual int orderCmd();
	virtual double orderLots();
	virtual double orderMarketPrice(const TaVDirRef& dir);
	virtual double orderStoploss(TaOrderOpeningAction* oA);
	virtual double orderTakeprofit(TaOrderOpeningAction* oA);
	virtual int exclusiveOrderType();

	//TODO move to another class->
	bool isContainsOrder(STRING symbol, int pOrderType);

};
#endif
