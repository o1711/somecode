#ifndef TaCollectionTickHandler_MQH
#define TaCollectionTickHandler_MQH

#include "../TaTickHandler.h"

class TaCollectionTickHandler: public TaTickHandler {

public:
	TaCollectionTickHandler(STRING name);
	virtual ~TaCollectionTickHandler();
	void add(TaTickHandlerRef& os);
	virtual void onTickInternal();
	TaTickHandlerRef get(int idx);
	int size();

protected:
	TaRefList * elementList;
	TaRef ref_elementList;

};
#endif
