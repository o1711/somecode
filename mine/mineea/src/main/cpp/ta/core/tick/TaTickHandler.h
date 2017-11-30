#ifndef TaTickHandler_MQH
#define TaTickHandler_MQH


class TaTickHandler : public TaContainerAwareObject {
   protected:
		STRING name;
		bool active;
   public: 
		TaTickHandler(STRING name);
		virtual ~TaTickHandler();
   	   
		virtual void onTick();
		virtual void onTickInternal();		
		virtual bool isActive();
};

class TaTickHandlerRef: public TaRef {
public:
	static TaTickHandlerRef Null(){
		TaTickHandlerRef ref;
		return ref;
	}
public:
	TaTickHandlerRef(){}
	TaTickHandlerRef(const TaRef& sr):TaRef(sr){}
	TaTickHandlerRef(TaTickHandler* pPointer):TaRef(pPointer){}
	TaTickHandler* o() const {
		return this->pointer;
	}
};
#endif
