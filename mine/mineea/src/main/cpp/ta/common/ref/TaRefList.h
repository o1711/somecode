#ifndef TaRefList_MQH
#define TaRefList_MQH
#include "../../system/TaRef.h"


class TaRefList :public TaObject{
	static const int INITIAL_SIZE;
	static const int STEP_SIZE;
protected:
	TaRefArray* elementArray;
public:
	TaRefList();
	virtual ~TaRefList();

	virtual void addRef(TaRef& ele);
	virtual void add(TaObject* ele);
	virtual TaObject* getObj(int idx);
	virtual TaRef get(int idx);
	virtual TaRef set(int idx, TaRef& ele);
	virtual TaRef remove(int idx);
	virtual int getSize();
	virtual bool isEmpty();
	virtual void clear(bool destroy);
	virtual STRING toString();
};

#endif
