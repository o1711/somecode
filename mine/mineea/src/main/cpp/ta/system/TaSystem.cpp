#include "TaSystem.h"

#ifdef MQL

template<typename T> int TaSystem::size(T arr[]){
	return ArraySize(arr);
}
template<typename T> int TaSystem::resize(T arr[],int size){
	return ArrayResize(arr,size);
}
template<typename T> void TaSystem::print(T value){
	Print(value);
}
void TaSystem::printString(STRING str){
	Print(str);
}
int TaSystem::webRequest(STRING method, STRING url, STRING cookie,
		STRING referer, int timeout, const char&data[], int data_size,char& result[],STRING& header2){
	WebRequest(method,url,cookie,referer,timeout,data,data_size,result,header2);
}

int TaSystem::webRequest(STRING method, STRING url, STRING headers, int timeout,
		const char& data[],char& result[], STRING& header2){
	Print("webRequest,method:",method,",url:",url,",headers:",headers);
	ResetLastError();
	return WebRequest(method,url,headers,timeout,data,result,header2);
	Print("webRequest");
}
int TaSystem::stringToCharArray(STRING str,char ca[],int from){
	StringToCharArray(str,ca,from,WHOLE_ARRAY,CP_UTF8);
}
STRING TaSystem::charArrayToString(char data[],int from){
	return CharArrayToString(data,from,WHOLE_ARRAY,CP_UTF8);
}

#endif


