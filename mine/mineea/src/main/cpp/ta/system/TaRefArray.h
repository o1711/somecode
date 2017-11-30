#ifndef TaRefArray_MQH
#define TaRefArray_MQH

#include "TaRef.h"


class TaRefArray{
	static const int DEFAULT_INITIAL_SIZE;
	static const int DEFAULT_STEP_SIZE;
protected:
	TaRef elementArray[];
	int size;

	int initialCapacity;

	int stepCapacity;

public:
	TaRefArray();
	TaRefArray(int initialCap,int stepCap);
	virtual ~TaRefArray();

	virtual void addRef(TaRef& ele);
	virtual void add(TaObject* ele);
	virtual TaRef get(int idx);
	virtual TaRef set(int idx, TaRef& ele);
	virtual TaRef remove(int idx);
	virtual int getSize();
	virtual bool isEmpty();
	virtual void clear(bool destroy);
	virtual STRING toString();
};

#endif
