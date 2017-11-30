#ifndef TA_COMMON_JSON_TAJSONVALUE_MQH_
#define TA_COMMON_JSON_TAJSONVALUE_MQH_

#include "../../system/TaObject.h"

class TaJsonValue: public TaObject {
public:
	TaJsonValue();
	~TaJsonValue();
	virtual bool isString();
	virtual bool isObject();
	virtual bool isArray();
	virtual STRING toJsonString();
	virtual STRING toString();
};

class TaJsonValueRef: public TaRef {

public:
	TaJsonValueRef(){}
	TaJsonValueRef(const TaRef& sr):TaRef(sr){
		if(this->pointer !=NULL){
					//TaLang::__Print("TaJsonValueRef(&):");
					//TaLang::__Print(this->pointer->getTypeName());
					//TaLang::__Print(this->pointer->toString());
				}
	}
	TaJsonValueRef(TaJsonValue* pPointer):TaRef(pPointer){}
	TaJsonValue* o() const {
		if(this->pointer !=NULL){
			//TaLang::__Print("o():");
			//TaLang::__Print(this->pointer->getTypeName());
			//TaLang::__Print(this->pointer->toString());
		}
		return this->pointer;
	}
};
#endif
