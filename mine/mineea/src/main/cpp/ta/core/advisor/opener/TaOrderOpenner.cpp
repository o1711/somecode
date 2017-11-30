#include "TaOrderOpenner.h"

TaOrderOpenner::TaOrderOpenner(int pStrategy,STRING pName) :
		TaContainerAwareObject() {
	this->strategy = pStrategy;
}

TaOrderOpenner::~TaOrderOpenner() {

}

bool TaOrderOpenner::tryOpenOrder(TaOrderOpeningActionRef& pA) {
	return false;
}
