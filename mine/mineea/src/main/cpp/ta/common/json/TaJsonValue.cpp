#include "TaJsonValue.h"

TaJsonValue::TaJsonValue() {
	this->typeName = "TaJsonValue";
}

TaJsonValue::~TaJsonValue() {

}

bool TaJsonValue::isString() {
	return false;
}
bool TaJsonValue::isObject() {
	return false;
}
bool TaJsonValue::isArray() {
	return false;
}

STRING TaJsonValue::toJsonString(){
	return "exception";
}

STRING TaJsonValue::toString(){
	return "TaJsonValue";
}
