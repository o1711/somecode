#ifndef TaRefSTACK_MQH
#define TaRefSTACK_MQH
#include "TaRefList.h"

class TaRefStack : public TaObject{

	protected:		
		TaRefList* elementList;
		TaRef ref_elementList;
	public:
		TaRefStack();
		virtual ~TaRefStack();
		
		virtual void push(TaRef& ele);
		virtual TaRef top();
		virtual TaRef pop();
		virtual int getSize();
		virtual void clear(bool destroy);
		virtual bool isEmpty();
		virtual STRING toString();
};

#endif
