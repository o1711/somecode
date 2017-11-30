/*
 * TaData.mqh
 *
 *  Created on: 2015Äê3ÔÂ29ÈÕ
 *      Author: wu
 */

#ifndef TADATA_MQH_
#define TADATA_MQH_
#include "../../system/TaLang.h"
class TaData : public TaObject{
public:
	TaData();
	~TaData();
	TaData* getProperty(STRING key);
	double getPropertyAsDouble(STRING key,double def);
	int getPropertyAsInt(STRING key , int def);

	STRING getPropertyAsString(STRING key);
	void setProperty(STRING key, TaData* value);

protected:
	TaStringRefMap* propertyMap;
};


#endif /* TADATA_MQH_ */
