#include "TaRefArray.h"

const int TaRefArray::DEFAULT_INITIAL_SIZE = 10;
const int TaRefArray::DEFAULT_STEP_SIZE = 10;

TaRefArray::TaRefArray() :
		initialCapacity(TaRefArray::DEFAULT_INITIAL_SIZE), stepCapacity(
				TaRefArray::DEFAULT_STEP_SIZE) {
	//TaLang::__Print("1initil array cap:",TaRefArray::INITIAL_SIZE);
	int succ = TaSystem::resize(this->elementArray, initialCapacity);
	//TaLang::__Print("2initil array cap:",succ);
}
TaRefArray::TaRefArray(int initialCap, int stepCap) :
		initialCapacity(initialCap), stepCapacity(stepCap) {
	int succ = TaSystem::resize(this->elementArray, initialCapacity);
}
TaRefArray::~TaRefArray() {

}
void TaRefArray::add(TaObject* ele) {
	TaRef ref(ele);
	this->addRef(ref);
}
void TaRefArray::addRef(TaRef& ele) {
	int cap = TaSystem::size(this->elementArray);
	//TaLang::__Print("this.size:",size);
	//TaLang::__Print("array cap:",cap);
	//TaLang::__Print("step",TaRefArray::STEP_SIZE);
	if (this->size == cap) {

		int newcap = this->size + this->stepCapacity;
		//TaLang::__Print("newcap",newcap);
		int succ = TaSystem::resize(this->elementArray, newcap);
		//TaLang::__Print("resized:",succ);
		if (succ == -1) {
			//TaLang::__Print("array resize failed.");
		}
	}

	this->elementArray[this->size] = ele;
	this->size++;
}

TaRef TaRefArray::get(int idx) {
	return this->elementArray[idx];
}

TaRef TaRefArray::set(int idx, TaRef& ele) {
	TaRef rt = this->elementArray[idx];
	this->elementArray[idx] = ele;
	return rt;
}

int TaRefArray::getSize() {
	return this->size;
}

TaRef TaRefArray::remove(int idx) {
	if (idx < 0 || idx > this->size - 1) {
		return NULL;
	}
	TaRef rt = this->elementArray[idx];
	for (int i = idx; i < this->size - 1; i++) {
		this->elementArray[i] = this->elementArray[i + 1];
	}
	this->size--;
	return rt;
}

void TaRefArray::clear(bool destroy) {
	this->size = 0;
}

bool TaRefArray::isEmpty() {
	return this->size == 0;
}

STRING TaRefArray::toString() {
	TaRef ref(new TaStringBuffer());
	TaStringBuffer* rt = ref.obj();
	rt->append("TaRefArray{");
	for (int i = 0; i < this->size; i++) {
		TaRef ele = this->get(i);
		rt->append(ele);
		rt->append(",");
	}
	rt->append("}");
	STRING rtt = rt->toString();
	return rtt;
}

