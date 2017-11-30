/*
 * TaWebRequest.cpp
 *
 *  Created on: 2015Äê4ÔÂ5ÈÕ
 *      Author: wu
 */


#include "TaWebRequest.h"

TaWebRequest::TaWebRequest() {
	TaWebHeadersRef ref(new TaWebHeaders());
	this->headers =ref;
}

TaWebRequest::~TaWebRequest() {
}

void TaWebRequest::setUrl(STRING url) {
	this->url = url;
}

TaWebHeadersRef TaWebRequest::getHeaders() {
	return this->headers;
}

void TaWebRequest::setTimeout(int timeout) {
	this->timeout = timeout;
}

int TaWebRequest::getTimeout() {
	return this->timeout;
}

void TaWebRequest::setBody(STRING body) {
	this->body = body;
}

STRING TaWebRequest::getBody() {
	return this->body;
}

STRING TaWebRequest::getUrl() {
	return this->url;
}
