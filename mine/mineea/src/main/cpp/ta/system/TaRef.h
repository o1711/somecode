/*
 * TaReference.mqh
 *
 *  Created on: 2015Äê4ÔÂ1ÈÕ
 *      Author: wu
 */

#ifndef TAREFERENCE_MQH_
#define TAREFERENCE_MQH_
#include "TaObject.h"
#include "TaLang.h"
class TaRef_Counter {
public:
	static int nextId;
public:
	int counter;
public:
	int id;
	TaRef_Counter();
	~TaRef_Counter();

	int add();
	int release();
};

class TaRef {

public:
	static int nextId;
public:
	static TaRef ref(TaObject * obj);
protected:
	TaObject* pointer;
	TaRef_Counter* rc;

public:
	int id;
	TaRef();
	TaRef(const TaRef& sr);
	TaRef(TaObject* pPointer);
	~TaRef();
	int counter();
	STRING toString();
	TaObject* obj();
	TaRef* operator =(const TaRef& sref);
	bool isNull();
};

#endif /* TAREFERENCE_MQH_ */
