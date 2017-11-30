#include "TaCollectionTickHandler.h"

TaCollectionTickHandler::TaCollectionTickHandler(STRING pName) : TaTickHandler(pName){
	this->elementList = new TaRefList();
	this->ref_elementList =TaRef::ref(this->elementList);
}

TaCollectionTickHandler::~TaCollectionTickHandler(){

}

void TaCollectionTickHandler::onTickInternal() {

	for (int i = 0; i < this->size(); i++) {
		TaTickHandlerRef osI = this->get(i);
		osI.o()->onTick();
	}
}

TaTickHandlerRef TaCollectionTickHandler::get(int idx) {
	return this->elementList->get(idx);
}

int TaCollectionTickHandler::size() {
	return this->elementList->getSize();
}

void TaCollectionTickHandler::add(TaTickHandlerRef& pOs) {
	this->elementList->addRef(pOs);
}

