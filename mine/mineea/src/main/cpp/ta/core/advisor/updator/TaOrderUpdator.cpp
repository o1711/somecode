#include "TaOrderUpdator.h"


TaOrderUpdator::TaOrderUpdator(STRING pName) :
		TaContainerAwareObject() {
	this->name = pName;
}

TaOrderUpdator::~TaOrderUpdator() {
}

bool TaOrderUpdator::tryUpdateOrder(TaOrderUpdatingActionRef& uAr) {
	return false;
}
