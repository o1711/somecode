#ifndef TaNewBarOrderAdvisor_MQH
#define TaNewBarOrderAdvisor_MQH


#include "../TaOrderAdvisor.h"
#include "TaBaseOrderAdvisor.h"

class TaNewBarOrderAdvisor: public TaBaseOrderAdvisor {
protected:
	TaDateTime* lastBarTime;
	TaDateTime* tmpTaDateTime;

public:
	TaNewBarOrderAdvisor();
	virtual ~TaNewBarOrderAdvisor();
	virtual bool tryOpenOrder(TaOrderOpeningActionRef& pAr);
	virtual bool tryOpenOrderOnNewBar(TaOrderOpeningActionRef& pAr);
	virtual void configure(TaConfigRef& config);
};
#endif
