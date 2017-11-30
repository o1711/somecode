
#ifndef TA_LOGGER_CONFIGUATION_MQH_
#define TA_LOGGER_CONFIGUATION_MQH_

#include "../../system/TaLang.h"
#include "../lang/TaInteger.h"
#include "TaLogger.h"

class TaLoggerConfiguration: public TaObject {

public:
	TaLoggerConfiguration();
	virtual ~TaLoggerConfiguration();
	int getLogLevel(STRING pName);
protected:
	int rootLevel;
	TaStringRefMap* levelMap;
};

#endif /*TA_LOGGER_CONFIGUATION_MQH_*/
