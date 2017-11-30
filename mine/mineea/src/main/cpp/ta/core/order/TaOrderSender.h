#ifndef TaOrderSender_MQH
#define TaOrderSender_MQH

#include "../../common/lang/TaVDir.h"
#include "../advisor/opener/TaOrderOpenner.h"
#include "../risk/TaTotalLossWeeklyRiskControl.h"

class TaOrderSender: public TaContainerAwareObject {

protected:

	TaRiskControl* riskControl;
	TaRef ref_riskControl;

public:
	TaOrderSender();
	virtual ~TaOrderSender();

	bool trySendOrder(TaOrderOpeningAction* oA);

	bool doSendOrder(TaOrderOpeningAction* oA);
	virtual void setContainer(TaContainerRef& cref);
};
#endif
