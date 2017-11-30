
#ifndef TA_JSONCONFIG_FACTORY_MQH
#define TA_JSONCONFIG_FACTORY_MQH
#include "TaConfigFactory.h"
#include "../json/TaJsonObject.h"
class TaJsonConfigFactory : public TaConfigFactory{

public:
	TaJsonConfigFactory(STRING configFile);
	virtual ~TaJsonConfigFactory();

	virtual TaRef loadConfig();
private:
	STRING readFile(STRING resource);
	TaJsonValueRef readFileAsJson(STRING resource);
	void convertFieldToConfig(TaConfig* parent,STRING key, TaJsonValueRef& json);
	TaConfig* convertToConfig(STRING type,TaJsonValueRef& json);
};

#endif;
