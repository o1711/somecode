
#ifndef TA_LOGGER_FACTORY_MQH_
#define TA_LOGGER_FACTORY_MQH_

#include "TaLogger.h"
#include "TaLoggerConfiguration.h"

class TaLoggerFactory: public TaObject {

public:
	static TaLoggerRef getLogger(STRING name);
	static TaRef getInstance();
	static TaRef ME;

public:
	TaLoggerFactory();
	virtual ~TaLoggerFactory();

	TaLoggerRef doGetLogger(STRING name);
	TaLoggerConfiguration* loggerConfiguration;
	TaRef ref_loggerConfiguration;

	TaStringRefMap* loggerMap;
	TaRef ref_loggerMap;
};

#endif /*#ifndef TA_LOGGER_FACTORY_MQH_*/
