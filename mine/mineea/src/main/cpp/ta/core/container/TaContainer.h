#ifndef TA_CONTAINER_MQH
#define TA_CONTAINER_MQH

#include "TaContainerAwareObject.h"
#include "../../common/TaNotifier.h"
#include "../../common/ref/TaStringRefMap.h"
#include "../../common/log/TaLoggerFactory.h"

class TaContainer: public TaObject {

private:
	static TaLoggerRef LOG;
public:
	TaContainer();
	virtual ~TaContainer();
	TaNotifier* getNotifier();
	TaRef getComponent(STRING id);
	void addComponent(STRING id, TaRef& component);
	void destroy();

private:
	TaDateTime* lastBarTime;
	TaNotifier* notifier;
	TaStringRefMap* componentMap;
};

class TaContainerRef: public TaRef {
public:
	static TaContainerRef Null(){
		TaContainerRef ref;
		return ref;
	}
public:
	TaContainerRef(){}
	TaContainerRef(const TaRef& sr):TaRef(sr){}
	TaContainerRef(TaContainer* pPointer):TaRef(pPointer){}
	TaContainer* o() const {
		return this->pointer;
	}
};
#endif/* TA_CONTAINER_MQH*/
