#include "TaJsonParser.h"

static const int TaJsonParser_State::TYPE_TaJsonParser_State_Init = 1;
static const int TaJsonParser_State::TYPE_TaJsonParser_State_Object = 2;
static const int TaJsonParser_State::TYPE_TaJsonParser_State_Object_Field = 3;
static const int TaJsonParser_State::TYPE_TaJsonParser_State_Array = 4;
static const int TaJsonParser_State::TYPE_TaJsonParser_State_String = 5;

/**
 *
 */
TaJsonParser_State::TaJsonParser_State(int pType, TaRefStack* stack) {
	this->typeName = "TaJsonParser_State";
	this->errorMessage = NULL;
	this->type = pType;
	this->stack = stack;
	this->logger = TaLoggerFactory::getLogger(
			"ta.common.json.TaJsonParser_State");
}

TaJsonParser_State::~TaJsonParser_State() {
	////delete this->logger; never //delete this.
}

TaJsonValueRef TaJsonParser_State::getJsonValue() {
	return this->jsonValue;
}
void TaJsonParser_State::push(TaRef& stateRef) {
	this->stack->push(stateRef);
}

bool TaJsonParser_State::hasError() {
	return this->errorMessage != NULL;
}

STRING TaJsonParser_State::getError() {
	return this->errorMessage;
}

void TaJsonParser_State::processNextChar(USHORT nextChar) {

}

void TaJsonParser_State::onChildPoped(TaJsonParser_State* state) {

}

bool TaJsonParser_State::isEmptyChar(USHORT nextChar) {
	return (nextChar == ' ' || nextChar == '\t');
}

void TaJsonParser_State::assertEmptyChar(USHORT nextChar) {
	if (this->isEmptyChar(nextChar)) {
		return;
	}
	TaStringBufferRef sb(new TaStringBuffer());
	this->setError(
			sb.o()->append("unexpected char,empty char expected,but got:")->append(
					nextChar)->append(",state:")->append(this->getPointer())->toString());
	//delete sb;
}

bool TaJsonParser_State::waiting(USHORT nextChar, USHORT theChar) {
	if (this->isEmptyChar(nextChar)) {
		return false;
	} else {
		if (nextChar == theChar) {
			return true;
		}
		TaStringBufferRef sb(new TaStringBuffer());
		this->setError(
				sb.o()->append("unexpected char,waiting:")->append(theChar)->toString());
		//delete sb;
		return false;
	}
}

TaRef TaJsonParser_State::waitingStringState(USHORT nextChar,
		bool pushToStack) {
	TaRef rt;
	if (this->waiting(nextChar, '\"')) {
		TaRef ref(new TaJsonParser_State_String(this->stack));
		rt = ref;
		if (pushToStack) {
			this->stack->push(rt);
		}
	}
	return rt;
}

TaRef TaJsonParser_State::waitingAnyState(USHORT nextChar, bool pushToStack) {

	TaRef rt;
	if (this->isEmptyChar(nextChar)) {
		return rt;
	}
	TaJsonParser_State* state = NULL;
	switch (nextChar) {
	case '{':
		state = new TaJsonParser_State_Object(this->stack);
		break;
	case '[':
		state = new TaJsonParser_State_Array(this->stack);
		break;
	case '\"':
		state = new TaJsonParser_State_String(this->stack);
		break;
	default:
		TaStringBufferRef sb(new TaStringBuffer());
		this->setError(
				sb.o()->append("unexpected char:")->append(nextChar)->append(
						",waiting one of {,[ or \"")->toString());
		//delete sb;
		break;
	}
	TaRef rt2(state);
	rt = rt2;
	if (pushToStack && !rt.isNull()) {
		this->stack->push(rt); //
	}
	return rt;
}

void TaJsonParser_State::setError(STRING message) {
	this->errorMessage = message;
}
void TaJsonParser_State::pop() {
	this->stack->pop();
	TaRef top = this->stack->top();
	TaJsonParser_State* state = top.obj();
	state->onChildPoped((TaJsonParser_State*) this->getPointer());
}
STRING TaJsonParser_State::toString() {
	TaStringBufferRef sb(new TaStringBuffer());
	sb.o()->append(this->typeName);
	sb.o()->append(this->type);
	return sb.o()->toString();
}
/**
 *TaJsonParser_State_Init
 */
TaJsonParser_State_Init::TaJsonParser_State_Init(TaRefStack* stack) :
		TaJsonParser_State(TYPE_TaJsonParser_State_Init, stack) {

}
TaJsonParser_State_Init::~TaJsonParser_State_Init() {

}

void TaJsonParser_State_Init::processNextChar(USHORT nextChar) {
	if (this->jsonValue.isNull()) {
		this->waitingAnyState(nextChar, true);
	} else {
		this->assertEmptyChar(nextChar);
	}

}

void TaJsonParser_State_Init::onChildPoped(TaJsonParser_State* state) {
	this->jsonValue = state->getJsonValue();
	TaStringBufferRef sb(new TaStringBuffer());

	this->logger.o()->info(
			sb.o()->append("TaJsonParser_State_Init::onChildPoped")->append(
					this->jsonValue)->toString());

	//delete sb;
}

/**
 *TaJsonParser_State_Object
 */
TaJsonParser_State_Object::TaJsonParser_State_Object(TaRefStack* stack) :
		TaJsonParser_State(TYPE_TaJsonParser_State_Object, stack) {
	this->startNext = true;
	TaRef ref(new TaStringRefMap());
	this->ref_fieldMap = ref;

	this->fieldMap = this->ref_fieldMap.obj();
}
TaJsonParser_State_Object::~TaJsonParser_State_Object() {

}
void TaJsonParser_State_Object::pop() {
	TaJsonValueRef jsonR(new TaJsonObject());
	TaJsonObject* jsonO = jsonR.o();

	TaRefList* keyList = this->fieldMap->getKeyList();
	for (int i = 0; i < keyList->getSize(); i++) {
		TaString* str = keyList->get(i).obj();
		STRING key = str->stringValue;
		TaJsonValueRef jsonV = this->fieldMap->getRef(key);
		jsonO->setProperty(str->stringValue, jsonV);
	}
	this->jsonValue = jsonR;
	TaJsonParser_State::pop();
}
void TaJsonParser_State_Object::processNextChar(USHORT nextChar) {

	switch (nextChar) {
	case '}':
		this->pop();
		return;
	}

	if (this->startNext) {

		TaRef state = this->waitingStringState(nextChar, false);

		if (!state.isNull()) {
			TaRef ref(new TaJsonParser_State_Object_Field(this->stack));
			this->push(ref);
			this->push(state);
		}
	} else {
		switch (nextChar) {
		case ',':
			this->startNext = true;
			break;
		default:
			this->assertEmptyChar(nextChar);
			break;
		}
	}

}
void TaJsonParser_State_Object::onChildPoped(TaJsonParser_State* state) {
	TaJsonParser_State_Object_Field* fieldState =
			(TaJsonParser_State_Object_Field*) state;
	TaRef value = fieldState->getJsonValue();
	this->fieldMap->put(fieldState->getKey(), value);
	this->startNext = false;
}

/**
 * TaJsonParser_State_Object_Field
 *
 */
TaJsonParser_State_Object_Field::TaJsonParser_State_Object_Field(
		TaRefStack* stack) :
		TaJsonParser_State(TYPE_TaJsonParser_State_Object_Field, stack) {
	this->colonGot = false;
}

TaJsonParser_State_Object_Field::~TaJsonParser_State_Object_Field() {

}
STRING TaJsonParser_State_Object_Field::getKey() {
	return this->key;
}

void TaJsonParser_State_Object_Field::processNextChar(USHORT nextChar) {
	if (this->key == NULL) {
		this->waitingStringState(nextChar, true);
	} else {
		if (this->colonGot) {
			this->waitingAnyState(nextChar, true);
		} else {
			this->colonGot = this->waiting(nextChar, ':');
		}
	}
}
void TaJsonParser_State_Object_Field::onChildPoped(TaJsonParser_State* state) {
	if (this->key == NULL) {
		TaJsonParser_State_String* keyState = (TaJsonParser_State_String*) state;
		this->key =
				((TaJsonString*) keyState->getJsonValue().o())->getStringValue();
	} else {
		//is value part
		this->jsonValue = state->getJsonValue();
		this->pop();
	}

}

//TaJsonParser_State_Array
TaJsonParser_State_Array::TaJsonParser_State_Array(TaRefStack* stack) :
		TaJsonParser_State(TYPE_TaJsonParser_State_Array, stack) {
	//TaLang::__Print("befoer");
	TaRef ref(new TaRefList());
	//TaLang::__Print("after");

	this->ref_elementList = ref;
	this->elementList = this->ref_elementList.obj();
	this->startNext = true;
}

TaJsonParser_State_Array::~TaJsonParser_State_Array() {
	//delete this->elementList;
}
void TaJsonParser_State_Array::pop() {
	TaJsonValueRef jsonR(new TaJsonArray());
	TaJsonArray* json = jsonR.o();

	for (int i = 0; i < this->elementList->getSize(); i++) {
		TaJsonValueRef ele = this->elementList->get(i);
		json->add(ele);
	}
	this->jsonValue = jsonR;
	TaJsonParser_State::pop();
}
void TaJsonParser_State_Array::onChildPoped(TaJsonParser_State* state) {
	TaRef ref = state->getJsonValue();
	this->elementList->addRef(ref); //
	this->startNext = false;
}
void TaJsonParser_State_Array::processNextChar(USHORT nextChar) {

	switch (nextChar) {
	case ',':
		this->startNext = true;
		break;
	case ']':
		this->pop();
		break;
	default:
		if (this->startNext) {
			this->waitingAnyState(nextChar, true);
		} else {
			this->assertEmptyChar(nextChar);
		}
		break;
	}

}

//TaJsonParser_State_String
TaJsonParser_State_String::TaJsonParser_State_String(TaRefStack* stack) :
		TaJsonParser_State(TYPE_TaJsonParser_State_String, stack) {
	TaStringBufferRef ref(new TaStringBuffer());
	this->stringBuffer = ref;
}
TaJsonParser_State_String::~TaJsonParser_State_String() {

}
void TaJsonParser_State_String::processNextChar(USHORT nextChar) {
	switch (nextChar) {
	case '\"':
		this->pop();
		break;
	case '{':
	case '}':
	case '[':
	case ']':
		this->setError("unexpected char.");
		break;
	default:
		this->stringBuffer.o()->append(nextChar);
		break;
	}
}

void TaJsonParser_State_String::pop() {
	TaJsonValueRef ref(new TaJsonString(this->stringBuffer.o()->toString()));
	this->jsonValue = ref;
	TaJsonParser_State::pop();
}

//Parser:

TaJsonParser::TaJsonParser() {
	this->logger = TaLoggerFactory::getLogger("ta.common.json.TaJsonParser");
	this->logger.o()->info("TaJsonParser1");

	this->stateStack = new TaRefStack();
	this->ref_stateStack = TaRef::ref(this->stateStack);//

	this->logger.o()->info("TaJsonParser2");
	//TODO exception got when getLogger here,why?
}

TaJsonParser::~TaJsonParser() {

}

TaJsonValueRef TaJsonParser::parseJsonString(STRING jsonString) {

	TaRef ref(new TaJsonParser_State_Init(this->stateStack));
	TaRef topRef = ref;

	this->stateStack->push(ref);

	int index = 0;
	STRING errorMessage = NULL;
	USHORT nextChar = 0;
	int len = TaLang::__StringLen(jsonString);

	for (int i = 0; i < len; i++) {

		index++;
		nextChar = TaLang::__StringGetCharacter(jsonString, i);
		((TaJsonParser_State*)topRef.obj())->processNextChar(nextChar);
		if (((TaJsonParser_State*)topRef.obj())->hasError()) {
			errorMessage = ((TaJsonParser_State*)topRef.obj())->getError();
			break;
		}
		topRef = this->stateStack->top();

	}

	if (errorMessage != NULL) {
		TaStringBufferRef sb(new TaStringBuffer());
		TaRef sbR(sb);
		sb.o()->append(errorMessage)->append(",index:")->append(index)->append(
				",char:")->append(nextChar)->append(",stack:");
		sb.o()->append(this->stateStack);
		sb.o()->append(",text parsed for now:");
		sb.o()->append(TaLang::__StringSubstr(jsonString, 0, index));
		this->logger.o()->error(sb.o()->toString());
		return NULL;
	}
	topRef = this->stateStack->pop();
	if (topRef.isNull()) {
		this->logger.o()->error("unexpected null top.");
		return NULL; //error
	}
	if (!this->stateStack->isEmpty()) {
		this->logger.o()->error("unexpected stack not empty.");
		return NULL; //error;
	}

	TaJsonParser_State* topS = topRef.obj();
	return topS->getJsonValue();
}
