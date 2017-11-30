#include "TaBaseOrderAdvisor.h"

TaBaseOrderAdvisor::TaBaseOrderAdvisor() :
		TaOrderAdvisor() {

}

TaBaseOrderAdvisor::~TaBaseOrderAdvisor() {
	delete this->openner;
}

void TaBaseOrderAdvisor::configure(TaConfigRef& config){
	TaOrderAdvisor::configure(config);
}
bool TaBaseOrderAdvisor::tryOpenOrder(TaOrderOpeningActionRef& pAr) {
	return this->openner->tryOpenOrder(pAr);
}
