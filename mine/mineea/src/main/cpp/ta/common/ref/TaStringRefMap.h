#ifndef TaStringRefMap_MQH
#define TaStringRefMap_MQH

#include "TaRefList.h"

class TaStringRefMap_Entry: public TaObject {
public:
	STRING key;
	TaRef value;
public:
	virtual STRING toString();
};

class TaStringRefMap: public TaObject {

protected:
	TaRefList* entryList;
	TaRef ref_entryList;
	TaRefList* cache_KeyList;
	TaRef ref_cache_KeyList;
public:
	TaStringRefMap();
	virtual ~TaStringRefMap();

	virtual TaObject* put(STRING key, TaObject* ele);
	virtual TaRef put(STRING key, TaRef& ele);
	virtual TaRef getRef(STRING key);
	virtual TaObject* getObj(STRING key);
	virtual int getSize();
	virtual void clear(bool destroy);
	TaRefList* getKeyList();
	virtual STRING toString();

private:
	virtual TaRef unPut(STRING key);
	void clearCache();
};

#endif
