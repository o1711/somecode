#ifndef TaObject_MQH
#define TaObject_MQH

#include "TaLang.h"
class TaRefArray;
class TaObject {
public:
	TaObject();
	virtual ~TaObject();
	TaObject* getPointer();
	virtual STRING toString();
	virtual STRING getTypeName();
	TaObject* ref(TaObject* obj);
protected:
	STRING typeName;
	TaRefArray* refArray;
};

#endif
