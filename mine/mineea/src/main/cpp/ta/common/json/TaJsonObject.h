/*
 * TaJsonObject.mqh
 *
 *  Created on: 2015?3?30?
 *      Author: wuzhen
 */

#ifndef TA_COMMON_JSON_TAJSONOBJECT_MQH_
#define TA_COMMON_JSON_TAJSONOBJECT_MQH_

#include "../../system/TaTop.h"
#include "TaJsonValue.h"

class TaJsonObject :public TaJsonValue{
public:
	TaJsonObject();
	~TaJsonObject();

	TaJsonValueRef getProperty(STRING key);
	STRING getPropertyAsString(STRING key);
	void setProperty(STRING key, TaJsonValueRef& value);
	void setProperty(STRING key, TaJsonValue* value);
	void setProperty(STRING key, STRING value);
	TaRefList* getPropertyNameList();

	virtual bool isObject();
	virtual STRING toJsonString();
	virtual STRING toString();
protected:
	TaStringRefMap* propertyMap;
	TaRef ref_propertyMap;
};
class TaJsonObjectRef: public TaRef {
public:
	static TaJsonObjectRef Null(){
		TaJsonObjectRef ref;
		return ref;
	}
public:
	TaJsonObjectRef(){}
	TaJsonObjectRef(const TaRef& sr):TaRef(sr){}
	TaJsonObjectRef(TaJsonObject* pPointer):TaRef(pPointer){}
	TaJsonObject* o() const {
		return this->pointer;
	}
};
#endif
