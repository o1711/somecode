/*
 * TaWebClient.h
 *
 *  Created on: 2015Äê4ÔÂ5ÈÕ
 *      Author: wu
 */

#ifndef TAWEB_H_
#define TAWEB_H_

#include "TaWebRequest.h"
#include "TaWebResponse.h"

class TaWebClient: public TaObject {

public:

	TaWebResponseRef request(TaWebRequestRef& req);
private:


};

class TaWebClientRef: public TaRef {
public:
	static TaWebClientRef Null() {
		TaWebClientRef ref;
		return ref;
	}
public:
	TaWebClientRef() {
	}
	TaWebClientRef(const TaRef& sr) :
			TaRef(sr) {
	}
	TaWebClientRef(TaWebClient* pPointer) :
			TaRef(pPointer) {
	}
	TaWebClient* o() const {
		return this->pointer;
	}
};
#endif /* TAWEBREQUEST_H_ */
