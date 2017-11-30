#ifndef TaOrderAdvisor_MQH
#define TaOrderAdvisor_MQH


#include "../../common/lang/TaVDir.h"
#include "../container/TaContainerAwareObject.h"
#include "opener/TaOrderOpeningAction.h"
#include "updator/TaOrderUpdatingAction.h"
#include "../../common/config/TaConfigurable.h"
class TaOrderAdvisor: public TaContainerAwareObject {
protected:
	int id;
	STRING symbol;
	int timeframe;
public:
	TaOrderAdvisor();
	virtual ~TaOrderAdvisor();
	int getId();
	STRING getSymbol();
	int getTimeframe();

	virtual bool tryOpenOrder(TaOrderOpeningActionRef& pA);
	virtual bool tryUpdateOrder(TaOrderUpdatingActionRef& uAr);
	virtual void configure(TaConfigRef& config);
};

class TaOrderAdvisorRef: public TaRef {
public:
	static TaOrderAdvisorRef Null(){
		TaOrderAdvisorRef ref;
		return ref;
	}
public:
	TaOrderAdvisorRef(){}
	TaOrderAdvisorRef(const TaRef& sr):TaRef(sr){}
	TaOrderAdvisorRef(TaOrderAdvisor* pPointer):TaRef(pPointer){}
	TaOrderAdvisor* o() const {
		return this->pointer;
	}
};
#endif
