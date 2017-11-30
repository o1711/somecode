#ifndef TaOrderUpdator_MQH
#define TaOrderUpdator_MQH


#include "../../container/TaContainerAwareObject.h"
#include "TaOrderUpdatingAction.h"

class TaOrderUpdator: public TaContainerAwareObject {
protected:
	STRING name;
public:
	TaOrderUpdator(STRING name);
	virtual ~TaOrderUpdator();
	virtual bool tryUpdateOrder(TaOrderUpdatingActionRef& uAr);
};

#endif
