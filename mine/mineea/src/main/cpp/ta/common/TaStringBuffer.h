#ifndef STRINGBuffer_MQH
#define STRINGBuffer_MQH

#include "../system/TaTop.h"
#include "../system/TaObject.h"

class TaStringBuffer: public TaObject {

public:
	TaStringBuffer();
	~TaStringBuffer();
	TaStringBuffer* append(TaRef& pStr);
	TaStringBuffer* append(TaObject* pStr);
	TaStringBuffer* append(STRING pStr);
	TaStringBuffer* append(double pStr);
	TaStringBuffer* append(int pStr);
	TaStringBuffer* append(USHORT pStr);
	STRING toString();
protected:
	STRING buffer;
};

class TaStringBufferRef: public TaRef {
public:
	static TaStringBufferRef Null(){
		TaStringBufferRef ref;
		return ref;
	}
public:
	TaStringBufferRef(){}
	TaStringBufferRef(const TaRef& sr):TaRef(sr){}
	TaStringBufferRef(TaStringBuffer* pPointer):TaRef(pPointer){}
	TaStringBuffer* o() const {
		return this->pointer;
	}
};
#endif
