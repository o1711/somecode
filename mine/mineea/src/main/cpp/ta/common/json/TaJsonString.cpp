
#include "TaJsonString.h"

TaJsonString::TaJsonString(STRING value){
	this->value = value;
}

TaJsonString::~TaJsonString(){
}

STRING TaJsonString::getStringValue(){
	return this->value;
}

bool TaJsonString::isString() {
	return true;
}

STRING TaJsonString::toJsonString(){
	return "\""+this->value+"\"";
}

STRING TaJsonString::toString(){
	return this->value;
}
