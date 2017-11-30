#ifndef TaRiskControl_MQH
#define TaRiskControl_MQH

#include "../container/TaContainerAwareObject.h"
#include "../advisor/opener/TaOrderOpenner.h"

class TaRiskControl: public TaContainerAwareObject {

public:
	TaRiskControl();
	virtual ~TaRiskControl();
	virtual bool isOrderOpenAllowed(TaOrderOpeningAction * oA);

};

#endif
