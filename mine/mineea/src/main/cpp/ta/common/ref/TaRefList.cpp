#include "TaRefList.h"

TaRefList::TaRefList() {
	this->elementArray = new TaRefArray();
}

TaRefList::~TaRefList() {
	delete this->elementArray;
}

void TaRefList::add(TaObject* ele) {
	TaRef ref(ele);
	this->addRef(ref);
}
void TaRefList::addRef(TaRef& ele) {
	this->elementArray->addRef(ele);
}
TaObject* TaRefList::getObj(int idx){
	TaRef ref = this->get(idx);
	return ref.obj();
}
TaRef TaRefList::get(int idx) {
	return this->elementArray->get(idx);
}

TaRef TaRefList::set(int idx, TaRef& ele) {
	return this->elementArray->set(idx,ele);
}

int TaRefList::getSize() {
	return this->elementArray->getSize();
}

TaRef TaRefList::remove(int idx) {
	return this->elementArray->remove(idx);
}

void TaRefList::clear(bool destroy) {
	this->elementArray->clear(true);
}

bool TaRefList::isEmpty() {
	return this->elementArray->isEmpty();
}

STRING TaRefList::toString() {
	return this->elementArray->toString();
}

