/*
 * TaConfig.mqh
 *
 *  Created on: 2015Äê3ÔÂ29ÈÕ
 *      Author: wu
 */

#ifndef TADATA_MQH_
#define TADATA_MQH_
#include "../../system/TaLang.h"
#include "../log/TaLogger.h"
#include "../ref/TaStringRefMap.h"
class TaConfigRef;
class TaConfig: public TaObject {
private:
	static TaLoggerRef logger ;
public:
	TaConfig(STRING type);
	~TaConfig();
	STRING getType();
	STRING getProperty(STRING key);
	double getPropertyAsDouble(STRING key, double def);
	int getPropertyAsInt(STRING key, int def);
	void setProperty(STRING key, STRING value);
	TaConfigRef getChild(STRING type);
	int getChildListSize(STRING type);
	TaConfigRef getChild(STRING type, int idx);
	void addChild(TaConfigRef& child);
	virtual STRING toString();
protected:
	STRING type;
	TaStringRefMap* propertyMap;
	TaRef ref_propertyMap;
	TaRefList* childConfigList;
	TaRef ref_childConfigList;
};
class TaConfigRef: public TaRef {
public:
	static TaConfigRef Null(){
		TaConfigRef ref;
		return ref;
	}
public:
	TaConfigRef(){}
	TaConfigRef(const TaRef& sr):TaRef(sr){}
	TaConfigRef(TaConfig* pPointer):TaRef(pPointer){}
	TaConfig* o() const {
		return this->pointer;
	}
};
#endif /* TADATA_MQH_ */
