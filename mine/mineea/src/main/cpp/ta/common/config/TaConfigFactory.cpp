#include "TaConfigFactory.h"

TaConfigFactory::TaConfigFactory(STRING configFile) :
		TaContainerAwareObject() {
	this->configFile = configFile;
	this->initLogger("TaConfigFactory");
}

TaConfigFactory::~TaConfigFactory() {

}

TaRef TaConfigFactory::loadConfig() {
	TaRef ref;
	return ref;
}
