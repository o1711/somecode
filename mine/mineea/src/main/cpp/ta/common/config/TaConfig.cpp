#include "TaConfig.h"
#include "../lang/TaDouble.h"
#include "../lang/TaInteger.h"
#include "../log/TaLoggerFactory.h"

//TaLoggerRef TaConfig::logger = TaLoggerFactory::getLogger("TaConfig");

TaConfig::TaConfig(STRING pType) {
	this->type = pType;
	this->propertyMap = new TaStringRefMap();

	TaRef ref1(this->propertyMap);
	this->ref_propertyMap=ref1;
	//TaLang::__Print("before new TaRefList");
	this->childConfigList = new TaRefList();
	//TaLang::__Print("after new TaRefList");
	TaRef ref2(this->childConfigList);
	this->ref_childConfigList = ref2;
}

TaConfig::~TaConfig() {

}

STRING TaConfig::getType(){
	return this->type;
}
double TaConfig::getPropertyAsDouble(STRING key, double def) {
	STRING ele = this->getProperty(key);
	if (ele == NULL) {
		return def;
	}

	return TaDouble::parse(ele);
}

int TaConfig::getPropertyAsInt(STRING key, int def) {
	STRING ele = this->getProperty(key);
	if (ele == NULL) {
		return def;
	}

	return TaInteger::parse(ele);
}

STRING TaConfig::getProperty(STRING key) {
	TaRef ref = this->propertyMap->getRef(key);
	TaString * ele = ref.obj();
	if (ele == NULL) {
		return NULL;
	}
	return ele->stringValue;
}
int TaConfig::getChildListSize(STRING type) {
	int rt = 0;
	for (int i = 0; i < this->childConfigList->getSize(); i++) {
		TaConfig* cc = this->childConfigList->get(i).obj();
		if (cc->getType() == type) {
			rt++;
		}
	}
	return rt;
}
TaConfigRef TaConfig::getChild(STRING type) {
	return getChild(type, 0);
}
TaConfigRef TaConfig::getChild(STRING type, int idx) {
	TaConfigRef rt;
	int counter;
	for (int i = 0; i < this->childConfigList->getSize(); i++) {
		TaConfigRef cref =	this->childConfigList->get(i);

		if (cref.o()->getType() == type) {
			if (counter == idx) {
				rt = cref;
				break;
			}
			counter++;
		}
	}
	return rt;
}
void TaConfig::addChild(TaConfigRef& child) {
	this->childConfigList->addRef(child);
}

void TaConfig::setProperty(STRING key, STRING value) {
	TaString * valueObj = new TaString(value);
	TaRef ref(valueObj);
	this->propertyMap->put(key, ref);
}

STRING TaConfig::toString(){
	TaStringBufferRef sb(new TaStringBuffer());

	sb.o()->append("propertyMap:");
	sb.o()->append(this->propertyMap);
	sb.o()->append(",childConfigList:");
	sb.o()->append(this->childConfigList->toString());
	STRING rt = sb.o()->toString();
	return rt;
}
