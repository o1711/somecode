#include "TaRefStack.h"

TaRefStack::TaRefStack() {
	TaLang::__Print("TaRefStack1");
	TaRef ref(new TaRefList());
	TaLang::__Print("TaRefStack2");
	this->ref_elementList = ref;
	this->elementList = ref.obj() ;
}

TaRefStack::~TaRefStack() {
}

void TaRefStack::push(TaRef& ele) {
	//TaLang::__Print("push");
	if(ele.isNull()){
		//TaLang::__Print("push a null?");
	}
	this->elementList->addRef(ele);
}

TaRef TaRefStack::top() {
	int idx = this->getSize() - 1;
	if (idx < 0) {
		return NULL;
	}
	return this->elementList->get(idx);

}

TaRef TaRefStack::pop() {
	int idx = this->getSize() - 1;
	if (idx < 0) {
		return NULL;
	}
	TaRef rt = this->elementList->remove(idx);
	return rt;

}

int TaRefStack::getSize() {
	return this->elementList->getSize();
}
bool TaRefStack::isEmpty(){
	return this->elementList->isEmpty();
}
void TaRefStack::clear(bool destroy) {
	this->elementList->clear(destroy);
}
STRING TaRefStack::toString(){
	return this->elementList->toString();
	//return "TaRefStack";
}
