/*
 * TaWebHeaders.h
 *
 *  Created on: 2015Äê4ÔÂ5ÈÕ
 *      Author: wu
 */

#ifndef TAWEBHEADERS_H_
#define TAWEBHEADERS_H_
#include "../../system/TaObject.h"
#include "../../system/TaRef.h"
class TaWebHeaders: public TaObject {
public:
	static TaWebHeaders* decode(STRING headersData);
public:
	TaWebHeaders();
	~TaWebHeaders();
	void set(STRING key,STRING value);
	STRING get(STRING key);
	STRING encode();


protected:
	TaStringRefMap* headerMap;
};

class TaWebHeadersRef: public TaRef {
public:
	static TaWebHeadersRef Null(){
		TaWebHeadersRef ref;
		return ref;
	}
public:
	TaWebHeadersRef(){}
	TaWebHeadersRef(const TaRef& sr):TaRef(sr){}
	TaWebHeadersRef(TaWebHeaders* pPointer):TaRef(pPointer){}
	TaWebHeaders* o() const {
		return this->pointer;
	}
};
#endif /* TAWEBREQUEST_H_ */
