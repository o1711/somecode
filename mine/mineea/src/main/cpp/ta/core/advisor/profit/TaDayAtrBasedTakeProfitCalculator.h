#ifndef TaDayAtrBasedTakeProfitCalculator_MQH
#define TaDayAtrBasedTakeProfitCalculator_MQH

#include "../../container/TaContainerAwareObject.h"
#include "../TaTakeProfitCalculator.h"
class TaDayAtrBasedTakeProfitCalculator: public TaTakeProfitCalculator {
protected:

public:
	TaDayAtrBasedTakeProfitCalculator();
	virtual ~TaDayAtrBasedTakeProfitCalculator();
	virtual double calculate(TaOrderUpdatingActionRef& uAr);
};
#endif
