/*
 * TaSystem.h
 *
 *  Created on: 2015Äê4ÔÂ3ÈÕ
 *      Author: wu
 */

#ifndef TASYSTEM_H_
#define TASYSTEM_H_
#include "TaTop.h"

class TaSystem {
public:
	template<typename T> static int size(T arr[]);
	template<typename T> static int resize(T arr[], int size);
	template<typename T> static void print(T value);
	static void printString(STRING str);
	static int webRequest(STRING method, STRING url, STRING cookie,
			STRING referer, int timeout, const char&data[], int data_size,char& result[],STRING& header2);


	static int webRequest(STRING method, STRING url, STRING headers,
			int timeout, const char& body[], char& result[], STRING& header2);
	static int stringToCharArray(STRING str, char ca[], int from);
	static STRING charArrayToString(char data[], int from);
};

#endif /* TASYSTEM_H_ */
