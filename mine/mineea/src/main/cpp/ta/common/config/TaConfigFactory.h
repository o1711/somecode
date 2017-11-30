
#ifndef TA_CONFIG_FACTORY_MQH
#define TA_CONFIG_FACTORY_MQH
#include "../../core/container/TaContainerAwareObject.h"
#include "../../system/TaRef.h"
class TaConfigFactory : public TaContainerAwareObject{

public:
	TaConfigFactory(STRING configFile);
	virtual ~TaConfigFactory();

	virtual TaRef loadConfig();
protected:
	STRING configFile;
};

#endif;
