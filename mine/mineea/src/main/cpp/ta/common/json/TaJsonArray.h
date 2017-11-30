/*
 * TaJsonObject.mqh
 *
 *  Created on: 2015?3?30?
 *      Author: wuzhen
 */

#ifndef TA_COMMON_JSON_TAJSONARRAY_MQH_
#define TA_COMMON_JSON_TAJSONARRAY_MQH_
#include "TaJsonValue.h"

class TaJsonArray: public TaJsonValue {
public:

	TaJsonArray();
	~TaJsonArray();
	void add(TaJsonValueRef& value);
	void add(TaJsonValue* value);
	int getSize();
	TaJsonValueRef get(int idx);
	virtual bool isArray();
	virtual STRING toJsonString();
	virtual STRING toString();
protected:
	TaRefList* elementList;
	TaRef ref_elementList;
};

#endif /* TA_COMMON_JSON_TAJSONOBJECT_MQH_ */
