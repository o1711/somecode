#include "TaException.h"

TaException::TaException(STRING pMessage) {
	this->message = pMessage;
}

TaException::~TaException(){

}
STRING TaException::getMessage(){
	return message;
}
void TaException::throwException(STRING message){
	TaExceptionRef eref(new TaException(message));
	throwException(eref);
}
void TaException::throwException(TaExceptionRef& ref) {
	TaSystem::print(ref.o()->message);
	TaObject * obj = NULL;
	obj->toString();
}
