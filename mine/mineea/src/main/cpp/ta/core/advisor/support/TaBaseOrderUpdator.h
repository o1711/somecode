#ifndef TaBaseOrderUpdator_MQH
#define TaBaseOrderUpdator_MQH

#include "../updator/TaOrderUpdator.h"
#include "../../order/TaOrderExplainer.h"
#include "../updator/TaOrderUpdatingAction.h"
class TaBaseOrderUpdator: public TaOrderUpdator {

public:
	TaBaseOrderUpdator(STRING pName, TaOrderExplainer* pExplainer);
	virtual ~TaBaseOrderUpdator();

	virtual void closeOrder(int orderId);
	virtual void closeOrder(int orderId, double lotsToClose);
	virtual void modifyOrder(int orderId, double openPrice, double stoploss2,
			double takeprofit2);

protected:
	virtual TaOrderExplainer* getOrderExplainer();
	virtual double orderClosePrice();
	virtual const TaColorRef orderCloseTaColor();
protected:
	TaOrderExplainer* explainer;
};
#endif
