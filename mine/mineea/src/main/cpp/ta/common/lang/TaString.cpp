#include "TaString.h"
TaString::TaString(STRING pString) {
	this->typeName = "TaString";
	this->stringValue = pString;
}

TaString::~TaString() {

}

STRING TaString::toString(){
	return this->stringValue;
}
