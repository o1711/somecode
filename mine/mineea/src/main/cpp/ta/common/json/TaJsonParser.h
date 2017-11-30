#ifndef TA_COMMON_JSON_TAJSONPARSER_MQH_
#define TA_COMMON_JSON_TAJSONPARSER_MQH_

#include "../../system/TaTop.h"
#include "../../system/TaObject.h"
#include "TaJsonString.h"
#include "../ref/TaRefStack.h"
#include "../TaStringBuffer.h"

/**
 *
 */
class TaJsonParser_State: public TaObject {
public:
	static const int TYPE_TaJsonParser_State_Init;
	static const int TYPE_TaJsonParser_State_Object;
	static const int TYPE_TaJsonParser_State_Object_Field;
	static const int TYPE_TaJsonParser_State_Array;
	static const int TYPE_TaJsonParser_State_String;
protected:
	TaLoggerRef logger;
public:
	TaJsonParser_State(int type,TaRefStack* stack);
	~TaJsonParser_State();
	virtual void processNextChar(USHORT nextChar);
	TaRef waitingStringState(USHORT nextChar,bool pushToStack);
	TaRef waitingAnyState(USHORT nextChar,bool pushToStack);
	TaJsonValueRef getJsonValue();
	bool waiting(USHORT nextChar, USHORT theChar);
	bool isEmptyChar(USHORT nextChar);
	void assertEmptyChar(USHORT nextChar);
	bool hasError();
	STRING getError();
	void setError(STRING message);
	virtual void pop();
	void push(TaRef& stateRef);
	virtual void onChildPoped(TaJsonParser_State* state);
	virtual STRING toString();
protected:
	int type;
	TaRefStack* stack;
	STRING errorMessage;
	TaJsonValueRef jsonValue;
};

class TaJsonParser_State_Init: public TaJsonParser_State {
public:
	TaJsonParser_State_Init(TaRefStack* stack);
	~TaJsonParser_State_Init();
	virtual void processNextChar(USHORT nextChar);
	virtual void onChildPoped(TaJsonParser_State* state);
};


/**
 *
 */
class TaJsonParser_State_Object: public TaJsonParser_State {
public:
	TaJsonParser_State_Object(TaRefStack* stack);
	~TaJsonParser_State_Object();
	virtual void processNextChar(USHORT nextChar);
	virtual void pop();
	virtual void onChildPoped(TaJsonParser_State* state);
protected:
	bool startNext;
	TaStringRefMap* fieldMap;
	TaRef ref_fieldMap;
};



/**
 *
 */
class TaJsonParser_State_Object_Field: public TaJsonParser_State {
public:
	TaJsonParser_State_Object_Field(TaRefStack* stack);
	~TaJsonParser_State_Object_Field();
	virtual void processNextChar(USHORT nextChar);
	virtual void onChildPoped(TaJsonParser_State* state);
	STRING getKey();
protected:
	STRING key;
	bool colonGot;
};




/**
 *
 */
class TaJsonParser_State_Array: public TaJsonParser_State {
public:
	TaJsonParser_State_Array(TaRefStack* stack);
	~TaJsonParser_State_Array();
	virtual void processNextChar(USHORT nextChar);
	virtual void pop();
	virtual void onChildPoped(TaJsonParser_State* state);
protected:
	TaRefList* elementList;
	TaRef ref_elementList;
	bool startNext;
};

/**
 *
 */
class TaJsonParser_State_String: public TaJsonParser_State {
public:
	TaJsonParser_State_String(TaRefStack* stack);
	~TaJsonParser_State_String();
	virtual void processNextChar(USHORT nextChar);
	virtual void pop() ;

protected:
	TaStringBufferRef stringBuffer;
};

/**
 *
 */
class TaJsonParser :public TaObject{

public:
	TaJsonParser();
	~TaJsonParser();

	TaJsonValueRef parseJsonString(STRING jsonString);
protected:
	TaLoggerRef logger;
private:
	TaRefStack* stateStack;
	TaRef ref_stateStack;
};

#endif
