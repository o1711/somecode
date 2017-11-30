#ifndef TaTd0001Driver_MQH
#define TaTd0001Driver_MQH
#include "../../core/driver/TaDriver.h"
#include "TaTesterChart.h"
#include "../../core/tick/TaTickHandler.h"
#include "../../core/tick/support/TaOrderAdvisorTickHandler.h"
#include "../../core/tick/support/TaCollectionTickHandler.h"
#include "TaMa2OrderAdvisor.h"
class TaTd0001Driver: public TaDriver {
public:
	static TaLoggerRef LOG;
public:
	TaTd0001Driver(STRING configFile);
	virtual ~TaTd0001Driver();
	virtual void start();

};
#endif
