#include "TaJsonObject.h"

TaJsonObject::TaJsonObject() {
	TaRef ref(new TaStringRefMap());
	this->ref_propertyMap = ref;
	this->propertyMap = this->ref_propertyMap.obj();

}

TaJsonObject::~TaJsonObject() {
	this->propertyMap->clear(true);
}

TaJsonValueRef TaJsonObject::getProperty(STRING key) {
	TaJsonValueRef ref = this->propertyMap->getRef(key);
	return ref;
}

STRING TaJsonObject::getPropertyAsString(STRING key){
	TaJsonString* json = this->getProperty(key).o();
	if(json == NULL){
		return NULL;
	}
	return json->getStringValue();
}
void TaJsonObject::setProperty(STRING key, TaJsonValue* value) {
	TaJsonValueRef ref(value);
	this->setProperty(key,ref);
}

void TaJsonObject::setProperty(STRING key, TaJsonValueRef& value) {
	this->propertyMap->put(key, value);
}
void TaJsonObject::setProperty(STRING key, STRING value){
	TaJsonValueRef ref (new TaJsonString(value));
	this->setProperty(key,ref);
}
bool TaJsonObject::isObject() {
	return true;
}

TaRefList* TaJsonObject::getPropertyNameList(){
	return this->propertyMap->getKeyList();
}
STRING TaJsonObject::toJsonString(){
	TaStringBuffer* sb = new TaStringBuffer();

	TaStringBufferRef sbr(sb);
	TaRefList* kL = this->propertyMap->getKeyList();
	sb->append("{");

	for(int i=0;i<kL->getSize();i++){
		TaString* key = kL->get(i).obj();
		STRING keyI = key->stringValue;
		sb->append("\"");
		sb->append(keyI);
		sb->append("\":");
		TaJsonValueRef valueI = this->getProperty(keyI);
		STRING valueS = valueI.o()->toJsonString();
		sb->append(valueS);
		if(i < kL->getSize() -1){
			sb->append(",");
		}
	}
	sb->append("}");
	return sbr.o()->toString();
}
STRING TaJsonObject::toString(){
	return this->propertyMap->toString();
}
