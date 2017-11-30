/*
 * TaWebRequest.h
 *
 *  Created on: 2015Äê4ÔÂ5ÈÕ
 *      Author: wu
 */

#ifndef TAWEBREQUEST_H_
#define TAWEBREQUEST_H_
#include "../../system/TaObject.h"
#include "../../system/TaRef.h"
#include "TaWebHeaders.h"
class TaWebRequest: public TaObject {

public:
	TaWebRequest();
	~TaWebRequest();
	void setUrl(STRING url);
	TaWebHeadersRef getHeaders();
	void setTimeout(int timeout);
	int getTimeout();
	void setBody(STRING body);
	STRING getBody();
	STRING getUrl();
protected:
	TaWebHeadersRef headers;
	STRING url;
	int timeout;
	STRING body;
};

class TaWebRequestRef: public TaRef {
public:
	static TaWebRequestRef Null(){
		TaWebRequestRef ref;
		return ref;
	}
public:
	TaWebRequestRef(){}
	TaWebRequestRef(const TaRef& sr):TaRef(sr){}
	TaWebRequestRef(TaWebRequest* pPointer):TaRef(pPointer){}
	TaWebRequest* o() const {
		return this->pointer;
	}
};
#endif /* TAWEBREQUEST_H_ */
