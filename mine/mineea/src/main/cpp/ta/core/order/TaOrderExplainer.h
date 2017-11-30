#ifndef TaOrderExplainer_MQH
#define TaOrderExplainer_MQH

#include "../container/TaContainerAwareObject.h"

class TaOrderExplainer: public TaContainerAwareObject {
protected:

public:
	TaOrderExplainer();
	virtual ~TaOrderExplainer();

	double currentPrice();

	bool isOrderUp();

	bool isOrderProfitOfRisk(double originalRisk,double factor);

	bool isOrderProfitHalfRisk(double originalRisk);

	bool isOrderProfitOneRisk(double originalRisk);

	double getOrderProfitForPrice(double price);

	double getOrderOpenPrice();

	double getOrderStopLoss();

	double getOrderTakeProfit();

	const TaVDirRef getOrderDirection();

	virtual double getOrderOriginalRisk();

	virtual double getOrderOriginalLots();

	virtual double getOrderOriginalProfitRate();
};
#endif
