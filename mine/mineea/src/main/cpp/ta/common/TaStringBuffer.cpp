#include "TaStringBuffer.h"

TaStringBuffer::TaStringBuffer() {
	this->buffer = "";
}

TaStringBuffer::~TaStringBuffer() {

}
TaStringBuffer* TaStringBuffer::append(TaRef& ref) {
	return this->append(ref.obj());//
}
TaStringBuffer* TaStringBuffer::append(TaObject* pStr) {
	if(pStr == NULL){
		return this->append("NULL");//
	}
	return this->append(pStr->toString());
}
TaStringBuffer* TaStringBuffer::append(STRING pStr) {

	this->buffer = TaLang::__StringConcatenate(this->buffer, pStr);
	return this->getPointer();
}
TaStringBuffer* TaStringBuffer::append(double pStr) {
	return this->append(TaLang::__DoubleToString(pStr));
}
TaStringBuffer* TaStringBuffer::append(int pStr) {
	return this->append(TaLang::__IntegerToString(pStr));
}

TaStringBuffer* TaStringBuffer::append(USHORT pStr) {
	STRING pStr2 = TaLang::__CharToString(pStr);
	return this->append(pStr2);
}
STRING TaStringBuffer::toString(){
	return this->buffer;
}
