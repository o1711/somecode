#ifndef TaContainer_MQH
#define TaContainer_MQH

#include "../../system/TaObject.h"
#include "../../common/log/TaLogger.h"
#include "TaContainer.h"

class TaContainerAwareObject: public TaObject {

public:
	TaContainerAwareObject();
	virtual ~TaContainerAwareObject();

	virtual void setContainer(TaContainerRef& cc);

protected:
	void initLogger(STRING pLoggerName);
protected:
	TaLoggerRef logger;

};

#endif
