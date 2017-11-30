#include "TaLoggerConfiguration.h"
#include "../lang/TaInteger.h"
TaLoggerConfiguration::TaLoggerConfiguration() {
	this->rootLevel = TaLogger::INFO;
	this->levelMap = this->ref(new TaStringRefMap());
	this->levelMap->put(("ta"),new TaInteger(TaLogger::INFO));
	this->levelMap->put(("ta.core.driver.TaDriver"),new TaInteger(TaLogger::DEBUG));

}

TaLoggerConfiguration::~TaLoggerConfiguration() {
	this->levelMap->clear(true);//
	//delete this->levelMap;
}

int TaLoggerConfiguration::getLogLevel(STRING pName) {
	TaIntegerRef rt = this->levelMap->getRef(pName);
	if(!rt.isNull()){
		return rt.o()->intValue();
	}

	STRING comps[];
	int len = TaLang::__StringSplit(pName,TaLang::__StringGetCharacter(("."),0),comps);

	if(len == 0|| len == 1){
		return this->rootLevel;
	}else{
		int len2 = TaLang::__StringLen(pName) - TaLang::__StringLen(comps[len-1]) -1;
		STRING nameParent = TaLang::__StringSubstr(pName,0,len2);
		return this->getLogLevel(nameParent);
	}
}

