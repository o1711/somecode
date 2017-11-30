#ifndef TAWEB_RESULT_H
#define TAWEB_RESULT_H
#include "../../system/TaObject.h"
#include "../../system/TaRef.h"
class TaWebResponse: public TaObject {

public:
	TaWebResponse(int code,TaWebHeadersRef& headers,STRING body);
	~TaWebResponse();
	TaWebHeadersRef getHeaders();
	STRING getBody();
	int getReturnCode();
protected:
	int returnCode;
	TaWebHeadersRef headers;
	STRING body;
};

class TaWebResponseRef: public TaRef {
public:
	static TaWebResponseRef Null(){
		TaWebResponseRef ref;
		return ref;
	}
public:
	TaWebResponseRef(){}
	TaWebResponseRef(const TaRef& sr):TaRef(sr){}
	TaWebResponseRef(TaWebResponse* pPointer):TaRef(pPointer){}
	TaWebResponse* o() const {
		return this->pointer;
	}
};
#endif
