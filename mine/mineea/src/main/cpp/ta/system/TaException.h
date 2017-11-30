#ifndef TAEXCEPTION
#define TAEXCEPTION

#include "TaObject.h"

class TaExceptionRef;

class TaException :public TaObject{
public:
	static void throwException(TaExceptionRef& ref);
	static void throwException(STRING message);

public:
	TaException(STRING message);
	~TaException();
	STRING getMessage();
protected:
	STRING message;

};
class TaExceptionRef: public TaRef {
public:
	static TaExceptionRef Null(){
		TaExceptionRef ref;
		return ref;
	}
public:
	TaExceptionRef(){}
	TaExceptionRef(const TaRef& sr):TaRef(sr){}
	TaExceptionRef(TaException* pPointer):TaRef(pPointer){}
	TaException* o() const {
		return this->pointer;
	}
};
#endif
