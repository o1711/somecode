#ifndef TaTakeProfitCalculator_MQH
#define TaTakeProfitCalculator_MQH

#include "../container/TaContainerAwareObject.h"
#include "updator/TaOrderUpdatingAction.h"

class TaTakeProfitCalculator: public TaContainerAwareObject {

public:
	TaTakeProfitCalculator();
	virtual ~TaTakeProfitCalculator();
	double calculate(TaOrderUpdatingActionRef& uAr);
};

#endif
