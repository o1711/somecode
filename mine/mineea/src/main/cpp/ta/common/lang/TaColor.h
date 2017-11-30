#ifndef TaColor_MQH
#define TaColor_MQH
#include "../../system/TaObject.h"

class TaColor: public TaObject {

public:
	TaColor(int r, int g, int b);
protected:
	int red;
	int green;
	int blue;
};
class TaColorRef: public TaRef {
public:
	static TaColorRef Null(){
		TaColorRef ref;
		return ref;
	}
public:
	TaColorRef(){}
	TaColorRef(const TaRef& sr):TaRef(sr){}
	TaColorRef(TaColor* pPointer):TaRef(pPointer){}
	TaColor* o() const {
		return this->pointer;
	}
};
#endif
