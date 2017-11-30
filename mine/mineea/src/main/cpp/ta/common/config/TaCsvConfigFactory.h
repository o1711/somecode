
#ifndef TA_CSVCONFIG_FACTORY_MQH
#define TA_CSVCONFIG_FACTORY_MQH
#include "TaConfigFactory.h"

class TaCsvConfigFactory : public TaConfigFactory{

public:
	TaCsvConfigFactory(TaContainer* ct,STRING cfile);
	virtual ~TaCsvConfigFactory();

	virtual TaRef loadConfig();
private:

};

#endif;
