#ifndef TaOrderUpdatingAction_MQH
#define TaOrderUpdatingAction_MQH

#include "../../../common/TaAction.h"
#include "../../../common/lang/TaDouble.h"
#include "../../order/TaOrderComments.h"

class TaOrderUpdatingAction: public TaAction {

protected:
	int orderId;

public:
	TaOrderUpdatingAction(int orderId);
	virtual ~TaOrderUpdatingAction();
	int getOrderId();
};
class TaOrderUpdatingActionRef: public TaRef {
public:
	static TaOrderUpdatingActionRef Null(){
		TaOrderUpdatingActionRef ref;
		return ref;
	}
public:
	TaOrderUpdatingActionRef(){}
	TaOrderUpdatingActionRef(const TaRef& sr):TaRef(sr){}
	TaOrderUpdatingActionRef(TaOrderUpdatingAction* pPointer):TaRef(pPointer){}
	TaOrderUpdatingAction* o() const {
		return this->pointer;
	}
};
#endif
