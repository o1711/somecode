#include "TaTwoMovingAverageTimeBar.h"


TaTwoMovingAverageTimeBar::TaTwoMovingAverageTimeBar(int pDays1, int pDays2) :
		TaTimeBar() {
	this->ma1 = new TaMovingAverageTimeBar(pDays1);
	this->ma2 = new TaMovingAverageTimeBar(pDays2);

}

TaTwoMovingAverageTimeBar::~TaTwoMovingAverageTimeBar(){
	delete this->ma1;
	delete this->ma2;
}
double TaTwoMovingAverageTimeBar::getValue1(int idx) {
	return this->ma1->getValue(idx);
}

double TaTwoMovingAverageTimeBar::getMidValue(int idx) {
	return (this->ma1->getValue(idx) + this->ma2->getValue(idx))/2.0;
}

double TaTwoMovingAverageTimeBar::getValue2(int idx) {
	return this->ma2->getValue(idx);
}
bool TaTwoMovingAverageTimeBar::isAllOpenCloseInSideOfSlowMa(const TaVDirRef& dir, int idx1, int idx2){
	return this->ma2->isOCInSide(dir,idx1,idx2);
}
bool TaTwoMovingAverageTimeBar::isCloseInSideOfAllMa(const TaVDirRef& dir,
		int idx) {
	return this->ma1->isCloseInSide(dir, idx) && this->ma2->isCloseInSide(dir, idx);
}
bool TaTwoMovingAverageTimeBar::isOpenInSideOfAllMa(const TaVDirRef& dir,
		int idx) {
	return this->ma1->isOpenInSide(dir, idx) && this->ma2->isOpenInSide(dir, idx);
}

bool TaTwoMovingAverageTimeBar::isHLInSideOfAllMa(const TaVDirRef& dir,
		int idx) {

	return this->ma1->isHLInSide(dir, idx) && this->ma2->isHLInSide(dir, idx);
}
bool TaTwoMovingAverageTimeBar::isOCInSideOfAllMa(const TaVDirRef& dir,
		int idx) {
	return this->ma1->isOCInSide(dir, idx) && this->ma2->isOCInSide(dir, idx);
}

bool TaTwoMovingAverageTimeBar::isInSideOfAllMa(const TaVDirRef& dir, int idx,
		double value) {
	return this->ma1->isInSide(dir, idx, value)
			&& this->ma2->isInSide(dir, idx, value);
}

bool TaTwoMovingAverageTimeBar::isOCInSideOfAllMa(const TaVDirRef& dir, int idx1,
		int idx2) {
	return this->ma1->isOCInSide(dir, idx1, idx2)
			&& this->ma2->isOCInSide(dir, idx1, idx2);
}

bool TaTwoMovingAverageTimeBar::isHorLInSideOfAllMa(const TaVDirRef& dir, int idx){
	double mA1 = this->ma1->getValue(idx);
	double mA2 = this->ma2->getValue(idx);
	double high = this->getHigh(idx);
	double low = this->getLow(idx);


	if(dir.o()->isUp()){
		double max = TaLang::__MathMax(mA1,mA2);
		return dir.o()->isSignEquals(high - max) || dir.o()->isSignEquals(low - max);
	}else{
		double min = TaLang::__MathMax(mA1,mA2);
		return dir.o()->isSignEquals(high - min) || dir.o()->isSignEquals(low - min);
	}
}
