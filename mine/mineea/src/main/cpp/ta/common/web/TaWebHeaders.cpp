/*
 * TaWebRequest.cpp
 *
 *  Created on: 2015Äê4ÔÂ5ÈÕ
 *      Author: wu
 */


#include "TaWebHeaders.h"

TaWebHeaders::TaWebHeaders() {
	this->headerMap = new TaStringRefMap();
	this->ref(this->headerMap);
}

TaWebHeaders::~TaWebHeaders() {
}

TaWebHeaders* TaWebHeaders::decode(STRING headersData){
	TaWebHeaders* rt = new TaWebHeaders();

	return rt;
}

void TaWebHeaders::set(STRING key,STRING value){
	TaString* str = new TaString(value);
	this->headerMap->put(key,str);
}

STRING TaWebHeaders::get(STRING key){
	TaStringRef ref = this->headerMap->getObj(key);
	if(ref.isNull()){
		return NULL;
	}
	return ref.o()->stringValue;
}

STRING TaWebHeaders::encode(){
	TaStringBufferRef sbr(new TaStringBuffer());
	TaRefList* kl = this->headerMap->getKeyList();
	for(int i=0;i<kl->getSize();i++){
		TaString* keyO = kl->getObj(i);
		STRING key = keyO->stringValue;
		TaString* valueO = this->headerMap->getObj(key);
		STRING value =valueO->stringValue;

		sbr.o()->append(key);
		sbr.o()->append(": ");
		sbr.o()->append(value);
		sbr.o()->append("\r\n");
	}

	return sbr.o()->toString();
}
