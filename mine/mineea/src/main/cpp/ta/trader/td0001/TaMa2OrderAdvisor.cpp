#include "TaMa2OrderAdvisor.h"

TaMa2OrderAdvisor::TaMa2OrderAdvisor() :
		TaNewBarOrderAdvisor() {
	this->initLogger("TaMa2OrderAdvisor");
	this->orderLots_ = 0.02;

}
TaMa2OrderAdvisor::~TaMa2OrderAdvisor() {
	delete this->updator;

}
void TaMa2OrderAdvisor::configure(TaConfigRef& config) {
	TaNewBarOrderAdvisor::configure(config);
	STRING dirS = config.o()->getProperty("direct");
	TaVDirRef dir;
	if ("UP" == dirS) {
		dir = TaConstants::DIR_UP;
	} else {
		dir = TaConstants::DIR_DOWN;
	}
	this->direction = dir;

}
void TaMa2OrderAdvisor::setContainer(TaContainerRef& cref) {

	this->openner = new TaMa2OrderOpenner(this->getId(), (""), this->direction);
	this->openner->setContainer(cref);
	this->ref_openner = TaRef::ref(this->openner);
	((TaMa2OrderOpenner*) this->openner)->setOrderLots(this->orderLots_);
	((TaMa2OrderOpenner*) this->openner)->setTakeProfitRate(
			TaConstants::OST_MA2_TAKE_PROFIT_RATE);

	TaMa2OrderExplainer* exp = new TaMa2OrderExplainer();
	exp->setOrderOriginalLots(this->orderLots_);
	this->updator = new TaMa2OrderUpdator((""), exp);
	this->ref_updator = TaRef::ref(this->updator);

}

bool TaMa2OrderAdvisor::tryOpenOrderOnNewBar(TaOrderOpeningActionRef& pAr) {
	//this->logger.o()->debug("tryOpenOrderOnNewBar");
	return this->openner->tryOpenOrder(pAr);

}

bool TaMa2OrderAdvisor::tryUpdateOrder(TaOrderUpdatingActionRef& uAr) {

	this->updator->tryUpdateOrder(uAr);

	return true;
}
