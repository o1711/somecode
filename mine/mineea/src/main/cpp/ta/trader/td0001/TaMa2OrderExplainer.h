#ifndef TaMa2OrderExplainer_MQH
#define TaMa2OrderExplainer_MQH

#include "../../core/order/TaStagesOrderExplainer.h"

class TaMa2OrderExplainer: public TaStagesOrderExplainer {
protected:

public:

	TaMa2OrderExplainer();
	virtual ~TaMa2OrderExplainer();

	virtual double getOrderOriginalLots();
	virtual double getOrderOriginalProfitRate();
	virtual double getOrderStepLots();

	void setOrderOriginalLots(double pOrderOriginalLots);
protected:
	double orderOriginalLots;
};
#endif
