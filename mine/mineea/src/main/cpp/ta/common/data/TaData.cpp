#include "TaData.h"
#include "../lang/TaDouble.h"
#include "../lang/TaInteger.h"

TaData::TaData() {
	this->propertyMap = new TaStringRefMap();
}

TaData::~TaData() {
	this->propertyMap->clear(true);//
	//delete this->propertyMap;
}

TaData* TaData::getProperty(STRING key) {
	return this->propertyMap->get(key);
}
double TaData::getPropertyAsDouble(STRING key,double def){
	TaData* ele = this->getProperty(key);
	if(ele == NULL){
		return def;
	}
	TaDouble* db = (TaDouble*)ele;
	return db->doubleValue();
}

int TaData::getPropertyAsInt(STRING key,int def){
	TaData * ele = this->getProperty(key);
	if(ele == NULL){
		return def;
	}
	TaInteger* in = (TaInteger*)ele;
	return in->intValue();
}

STRING TaData::getPropertyAsString(STRING key){
	TaData * ele = this->getProperty(key);
	if(ele == NULL){
		return NULL;
	}
	return (STRING)ele;
}


void TaData::setProperty(STRING key, TaData* value) {
	this->propertyMap->put(key,value);
}
