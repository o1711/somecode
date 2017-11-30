#ifndef TaMa2OrderAdvisor_MQH
#define TaMa2OrderAdvisor_MQH

#include "../../core/timebar/TaMovingAverageTimeBar.h"
#include "../../core/advisor/TaOrderAdvisor.h"
#include "TaMa2OrderOpenner.h"
#include "../../core/advisor/support/TaNewBarOrderAdvisor.h"
#include "../../core/advisor/updator/TaOrderUpdator.h"
#include "TaMa2OrderUpdator.h"
#include "TaMa2OrderUpdator2.h"

class TaMa2OrderAdvisor: public TaNewBarOrderAdvisor {

protected:
	TaOrderUpdator* updator;
	TaRef ref_updator;
	TaVDirRef direction;
public:
	TaMa2OrderAdvisor();
	virtual ~TaMa2OrderAdvisor();
	virtual bool tryOpenOrderOnNewBar(TaOrderOpeningActionRef& pA);
	virtual bool tryUpdateOrder(TaOrderUpdatingActionRef& uAr);
	virtual void configure(TaConfigRef& config);
	virtual void setContainer(TaContainerRef& cc);
protected:
	double orderLots_;
};
#endif
