
#include "TaOrderAdvisor.h"

TaOrderAdvisor::TaOrderAdvisor() :
		TaContainerAwareObject() {
}

TaOrderAdvisor::~TaOrderAdvisor() {

}

void TaOrderAdvisor::configure(TaConfigRef& config){

	this->id = config.o()->getPropertyAsInt("id", 0);
	this->symbol = config.o()->getProperty("symbol");
	this->timeframe = config.o()->getPropertyAsInt("period",0);

}
int TaOrderAdvisor::getId(){
	return id;
}

STRING TaOrderAdvisor::getSymbol(){
	return this->symbol;
}

int TaOrderAdvisor::getTimeframe(){
	return this->timeframe;
}

bool TaOrderAdvisor::tryUpdateOrder(TaOrderUpdatingActionRef& uAr) {
	return false;
}

bool TaOrderAdvisor::tryOpenOrder(TaOrderOpeningActionRef& pA) {

	TaException::throwException("not implemented");
	return false;
}
