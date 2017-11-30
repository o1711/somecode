#ifndef TaStagesOrderExplainer_MQH
#define TaStagesOrderExplainer_MQH

#include "../container/TaContainerAwareObject.h"

class TaStagesOrderExplainer: public TaOrderExplainer {
protected:

public:
	TaStagesOrderExplainer();
	virtual ~TaStagesOrderExplainer();

	virtual int getOrderTotalStage();

	virtual int getOrderCurrentStage();

	virtual double getOrderStepLots();

};
#endif
