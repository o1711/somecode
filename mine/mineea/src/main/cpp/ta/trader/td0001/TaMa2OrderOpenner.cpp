
#include "TaMa2OrderOpenner.h"

TaMa2OrderOpenner::TaMa2OrderOpenner(int pStrategy,STRING pName, const TaVDirRef& dir) :
		TaBaseOrderOpenner(pStrategy,pName, dir) {
	this->initLogger("TaMa2OrderOpenner");
	this->maxBarsForWaitingMacd = 1;
	this->maxBarsForCrossing = 3;//5 is not good for EURUSD
	this->minBarsBeforeCrossing = 5;
	this->twoMa = new TaTwoMovingAverageTimeBar(20, 100);
}

TaMa2OrderOpenner::~TaMa2OrderOpenner() {
	delete this->twoMa;
}


bool TaMa2OrderOpenner::tryOpenOrder(TaOrderOpeningActionRef& pAr) {

	TaOrderOpeningAction* pA = pAr.o();
	//this->logger.o()->debug("tryOpen,0");
	int startIndex = this->getStartIndex();
	if (startIndex == -1) {
		return false; //no crossing happen->
	}
	//this->logger.o()->debug("tryOpen,1");
	int crossedIndex = this->getCrossedIndex(startIndex);

	if (crossedIndex == -1) { //no crossing happen
		return false;
	}

	if(crossedIndex > 1 + this->maxBarsForWaitingMacd){//crossed happen long ago->
		return false;
	}


	//this->logger.o()->debug("tryOpen,2");

	int macdFirstMatched = this->getMacdFirstMachedIndex(crossedIndex);

	if (macdFirstMatched == -1) { //no MACD matched->
		return false;
	}

	//this->logger.o()->debug("tryOpen,3");
	//if crossedINdex ==1
	//macdFirstMatched is 1 or 0,in both case, it's time to open order->
	//other wise,it must be the current bar to open order->

	if (crossedIndex > 1 && macdFirstMatched != 0) {
		//already created before->
		return false;
	}
	//this->logger.o()->debug("tryOpen,4");
	if(!this->isAllBeforeCrossingOpenCloseMatch(startIndex)){
		return false;
	}
	//this->logger.o()->debug("tryOpen,5");
	this->doOpenOrder(pAr, startIndex, crossedIndex);

	return true;
}
void TaMa2OrderOpenner::doOpenOrder(TaOrderOpeningActionRef& pAr,
		int startCrossingIndex, int crossedIndex) {
	////this->doLog("true,1");
	TaOrderOpeningAction* pA = pAr.o();
	double price = this->orderMarketPrice(this->direction);
	double risk = this->orderRisk(price, crossedIndex);
	double lots = this->orderLots();
	pA->setOrderCmd(this->direction.o()->isUp() ? TaLang::OP_BUY_ : TaLang::OP_SELL_);
	pA->setOrderPrice(price);
	pA->setOrderLots(lots); //
	pA->setOrderRisk(risk);
	////this->doLog("true,2");
	this->sendOrder(pA);
}

bool TaMa2OrderOpenner::isHLDeltaOk() {

	double hldAbs = this->twoMa->getHLDeltaAbs(1);
	double atr = TaLang::__iATR(NULL, 0, 100, 2);
	return hldAbs < atr * 5 && hldAbs > atr / 5;
}

int TaMa2OrderOpenner::getStartIndex() {
	//
	int rt = -1;
	for (int i = 1; i < 1 + this->maxBarsForCrossing; i++) {

		if (this->twoMa->isOpenInSideOfAllMa(
				TaConstants::getNegative(this->direction), i)) {
			rt = i;
			break;
		}

	}

	return rt;
}
int TaMa2OrderOpenner::getCrossedIndex(int startIndex) {
	int rt = -1;
	for (int i = startIndex; i >= 1; i--) {

		if (this->twoMa->isCloseInSideOfAllMa(this->direction, i)) {
			rt = i;
			break;
		}
	}

	return rt;
}

int TaMa2OrderOpenner::getMacdFirstMachedIndex(int crossedIndex) {
	int rt = -1;
	for (int i = crossedIndex; i >= 0; i--) {
		if (this->isMacdMatched(i)) {
			rt = i;
			break;
		}
	}
	return rt;
}

bool TaMa2OrderOpenner::isAllBeforeCrossingOpenCloseMatch(int startCrossingIndex) {

	if (!twoMa->isAllOpenCloseInSideOfSlowMa(TaConstants::getNegative(this->direction),
			startCrossingIndex + 1, startCrossingIndex + this->minBarsBeforeCrossing)) {
		//this->doLog("false,27");
		return false;
	}

	return true;
}

bool TaMa2OrderOpenner::isMacdMatched(int idx) {

	double macd0 = TaLang::__iMACD(NULL, 0, 12, 26, 9, TaLang::PRICE_CLOSE_, TaLang::MODE_MAIN_, idx);
	return this->direction.o()->isSignEquals(macd0);
}

double TaMa2OrderOpenner::orderLots(){
	return this->orderLots_;
}
void TaMa2OrderOpenner::setOrderLots(double pOrderLots){
	this->orderLots_ = pOrderLots;
}

double TaMa2OrderOpenner::orderRisk(double price, int crossedIndex) {
	double rt = 0.0;
	if (this->direction.o()->isUp()) {

		rt = price - this->twoMa->getLow(crossedIndex) + 0.00010;

	} else {
		rt = -price + this->twoMa->getHigh(crossedIndex) + 0.00010;
	}
		return rt;
}

