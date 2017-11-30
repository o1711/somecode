/*
 * TaWebRequest.cpp
 *
 *  Created on: 2015Äê4ÔÂ5ÈÕ
 *      Author: wu
 */

#include "TaWebClient.h"

TaWebResponseRef TaWebClient::request(TaWebRequestRef& req) {
	STRING method = "POST";
	STRING url = req.o()->getUrl();

	STRING header = req.o()->getHeaders().o()->encode();
	int timeout = 60000;// req.o()->getTimeout();
	STRING body = req.o()->getBody();

	char data[];

	TaSystem::stringToCharArray(body,data,0);
	TaSystem::print(body);
	TaSystem::print(TaSystem::size(data));
	TaSystem::resize(data,TaSystem::size(data)-1);

	char resultCA[];
	STRING header2;
	int code = 0;
	//code = TaSystem::webRequest(method,url,header,0,data,resultCA,header2);
	//TaSystem::print(url);
	code = TaSystem::webRequest("POST",url,NULL,NULL,timeout,data,0,resultCA,header2);
	//TaSystem::print(code);
	//TaSystem::print("code,result");
	//TaSystem::print(TaSystem::charArrayToString(resultCA,0));
	TaWebHeadersRef headers2(TaWebHeaders::decode(header2));
	STRING body2 = TaSystem::charArrayToString(resultCA,0);
	TaWebResponseRef rt(new TaWebResponse(code,headers2,body2));

	return rt;
}
