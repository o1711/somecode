#include "TaObject.h"

TaObject::TaObject() {
	this->typeName = "unknown";
	this->refArray = new TaRefArray(0, 2);
}

TaObject::~TaObject() {
	delete this->refArray;
}

STRING TaObject::getTypeName() {
	return this->typeName;
}

STRING TaObject::toString() {
	return "todo";
}
TaObject* TaObject::ref(TaObject* obj) {
	TaRef ref(obj);
	this->refArray->addRef(ref);//
	return obj;
}

TaObject* TaObject::getPointer() {
#ifdef MQL
	return GetPointer(this);
#else
	return this;
#endif
}
