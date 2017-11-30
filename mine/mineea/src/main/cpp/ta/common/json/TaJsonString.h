/*
 * TaJsonString.mqh
 *
 *  Created on: 2015?3?30?
 *      Author: wuzhen
 */

#ifndef TA_COMMON_JSON_TAJSONSTRING_MQH_
#define TA_COMMON_JSON_TAJSONSTRING_MQH_

#include "TaJsonValue.h"

class TaJsonString: public TaJsonValue {

public:
	TaJsonString(STRING value);
	~TaJsonString();
	STRING getStringValue();
	virtual bool isString();
	virtual STRING toJsonString();
	virtual STRING toString();

protected:

	STRING value;
};

#endif /* TA_COMMON_JSON_TAJSONSTRING_MQH_ */
