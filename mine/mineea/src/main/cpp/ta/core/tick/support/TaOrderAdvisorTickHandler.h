#ifndef TaOrderAdvisorTickHandler_MQH
#define TaOrderAdvisorTickHandler_MQH


#include "../../../common/lang/TaVDir.h"
#include "../../advisor/TaOrderAdvisor.h"
#include "../TaTickHandler.h"

class TaOrderAdvisorTickHandler: public TaTickHandler {

public:
	TaOrderAdvisorTickHandler(STRING pName);
	virtual ~TaOrderAdvisorTickHandler();
	virtual bool tryAddStrategy(TaOrderAdvisorRef& os,bool destroyIfFailed);
	virtual void onTickInternal();
	virtual void sendOrder(double risk);
	virtual void updateOrders();
	virtual TaOrderAdvisorRef getOrderAdvisor(int id);

	TaOrderComments* selectOriginalOrderComment(int orderId);
protected:
	virtual STRING getLoggerName();
protected:
	TaRefList* strategyList;
	TaRef ref_strategyList;
};
#endif
