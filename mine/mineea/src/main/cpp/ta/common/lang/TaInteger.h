#ifndef TaInteger_MQH
#define TaInteger_MQH
#include "../../system/TaObject.h"
class TaInteger: public TaObject {

public:
	static int parse(STRING pString);
public:
	TaInteger(int pValue);
	virtual ~TaInteger();
	int intValue();

private:
	int value;
};

class TaIntegerRef: public TaRef {
public:
	static TaIntegerRef Null(){
		TaIntegerRef ref;
		return ref;
	}
public:
	TaIntegerRef(){}
	TaIntegerRef(const TaRef& sr):TaRef(sr){}
	TaIntegerRef(TaInteger* pPointer):TaRef(pPointer){}
	TaInteger* o() const {
		return this->pointer;
	}
};
#endif
