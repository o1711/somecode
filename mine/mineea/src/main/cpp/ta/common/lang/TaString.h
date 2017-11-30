#ifndef TaString_MQH
#define TaString_MQH
#include "../../system/TaObject.h"
class TaString: public TaObject {
public:
	TaString(STRING pString);
	~TaString();
	STRING stringValue;
	virtual STRING toString();
};
class TaStringRef: public TaRef {
public:
	static TaStringRef Null(){
		TaStringRef ref;
		return ref;
	}
public:
	TaStringRef(){}
	TaStringRef(const TaRef& sr):TaRef(sr){}
	TaStringRef(TaString* pPointer):TaRef(pPointer){}
	TaString* o() const {
		return this->pointer;
	}
};
#endif
