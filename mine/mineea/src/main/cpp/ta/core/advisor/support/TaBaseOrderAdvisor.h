#ifndef TaBaseOrderAdvisor_MQH
#define TaBaseOrderAdvisor_MQH

#include "../../../common/lang/TaVDir.h"
#include "../TaOrderAdvisor.h"
#include "../../container/TaContainerAwareObject.h"
#include "../opener/TaOrderOpenner.h"

class TaBaseOrderAdvisor: public TaOrderAdvisor {

protected:
	TaOrderOpenner * openner;
	TaRef ref_openner;
public:
	TaBaseOrderAdvisor();
	virtual ~TaBaseOrderAdvisor();
	virtual bool tryOpenOrder(TaOrderOpeningActionRef& pAr);
	virtual void configure(TaConfigRef& config);
};
#endif
