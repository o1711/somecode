#include "TaJsonArray.h"

TaJsonArray::TaJsonArray() {
	TaRef ref(new TaRefList());
	this->ref_elementList = ref;
	this->elementList = this->ref_elementList.obj();
}
TaJsonArray::~TaJsonArray() {
}
void TaJsonArray::add(TaJsonValue* value){
	TaJsonValueRef ref(value);
	this->add(ref);
}
void TaJsonArray::add(TaJsonValueRef& value) {
	this->elementList->addRef(value);
}
int TaJsonArray::getSize() {
	return this->elementList->getSize();
}

TaJsonValueRef TaJsonArray::get(int idx) {
	return this->elementList->get(idx);
}
bool TaJsonArray::isArray(){
	return true;
}

STRING TaJsonArray::toJsonString(){
	TaStringBuffer* sb = new TaStringBuffer();

	TaStringBufferRef sbr(sb);

	sb->append("[");

	for(int i=0;i<this->getSize();i++){

		TaJsonValueRef valueI = this->get(i);
		STRING valueS = valueI.o()->toJsonString();
		sb->append(valueS);
		if(i < this->getSize() -1){
			sb->append(",");
		}
	}
	sb->append("]");
	return sbr.o()->toString();
}

STRING TaJsonArray::toString(){
	return this->elementList->toString();
}

