
#include "TaNewBarOrderAdvisor.h"

TaNewBarOrderAdvisor::TaNewBarOrderAdvisor() :
		TaBaseOrderAdvisor() {
	this->tmpTaDateTime = new TaDateTime();
}

TaNewBarOrderAdvisor::~TaNewBarOrderAdvisor() {
	delete this->tmpTaDateTime;
	delete this->lastBarTime;
}
void TaNewBarOrderAdvisor::configure(TaConfigRef& config){
	TaBaseOrderAdvisor::configure(config);
}
bool TaNewBarOrderAdvisor::tryOpenOrder(TaOrderOpeningActionRef& pAr) {
	TaDateTime* time0 = TaLang::getTime(0,this->tmpTaDateTime);
	//this->logger.o()->debug("tryOpenOrder,1");
	if (time0->isEquals(this->lastBarTime) ) {
		return false;
	}
	//this->logger.o()->debug("tryOpenOrder,2");
	if(this->lastBarTime == NULL){
		this->lastBarTime = new TaDateTime();
	}

	this->lastBarTime->set(time0);


	return this->tryOpenOrderOnNewBar(pAr);
}

bool TaNewBarOrderAdvisor::tryOpenOrderOnNewBar(TaOrderOpeningActionRef& pA) {

	return false;
}
