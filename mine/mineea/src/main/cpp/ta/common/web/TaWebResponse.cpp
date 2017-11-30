/*
 * TaWebRequest.cpp
 *
 *  Created on: 2015Äê4ÔÂ5ÈÕ
 *      Author: wu
 */


#include "TaWebResponse.h"
TaWebResponse::TaWebResponse(int pCode,TaWebHeadersRef& pHeaders,STRING pBody){
	this->returnCode = pCode;
	this->headers = pHeaders;
	this->body = pBody;
}
TaWebResponse::~TaWebResponse(){

}

int TaWebResponse::getReturnCode(){
	return this->returnCode;
}
TaWebHeadersRef TaWebResponse::getHeaders(){
	return this->headers;
}

STRING TaWebResponse::getBody(){
	return this->body;
}
