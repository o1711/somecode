#ifndef TaOrderOpenner_MQH
#define TaOrderOpenner_MQH

#include "../../../common/lang/TaVDir.h"
#include "../../container/TaContainerAwareObject.h"
#include "TaOrderOpeningAction.h"

class TaOrderOpenner: public TaContainerAwareObject {
protected:
	int strategy;
public:
	TaOrderOpenner(int strategy,STRING pName);
	virtual ~TaOrderOpenner();

	virtual bool tryOpenOrder(TaOrderOpeningActionRef& pA);

};
#endif
