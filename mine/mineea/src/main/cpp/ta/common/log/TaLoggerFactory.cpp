#include "TaLoggerFactory.h"

TaLoggerFactory::TaLoggerFactory() {

	this->loggerConfiguration = new TaLoggerConfiguration();
	this->ref_loggerConfiguration = TaRef::ref(this->loggerConfiguration);

	this->loggerMap = new TaStringRefMap();
	this->ref_loggerMap = TaRef::ref(this->loggerMap);
}

TaLoggerFactory::~TaLoggerFactory() {

}

TaRef TaLoggerFactory::ME;

TaRef TaLoggerFactory::getInstance() {
	if (TaLoggerFactory::ME.isNull()) {
		TaLoggerFactory::ME = TaRef::ref(new TaLoggerFactory());
		//TaGlobal::set("TA_LOGGER_FACTORY", TaLoggerFactory::ME); //
	}

	return TaLoggerFactory::ME;
}

TaLoggerRef TaLoggerFactory::getLogger(STRING pName) {
	//TaLang::__Print("getLogger:");
	//TaLang::__Print(pName);
	return ((TaLoggerFactory*)getInstance().obj())->doGetLogger(pName);
}

TaLoggerRef TaLoggerFactory::doGetLogger(STRING pName) {

	TaRef rt =	loggerMap->getRef(pName);
	//TaLang::__Print(obj->toString());

	if (rt.isNull()) {
		rt = TaRef::ref(new TaLogger(pName));
		loggerMap->put(pName, rt);
	}

	return rt;
}
