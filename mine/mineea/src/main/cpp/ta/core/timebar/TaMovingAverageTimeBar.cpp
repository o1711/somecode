#include "TaMovingAverageTimeBar.h"

TaMovingAverageTimeBar::TaMovingAverageTimeBar(int pDays) :
		TaTimeBar() {
	this->days = pDays;
}
TaMovingAverageTimeBar::~TaMovingAverageTimeBar(){

}
double TaMovingAverageTimeBar::getValue(int idx) {

	return TaLang::__iMA(NULL, 0, days, 0, TaLang::MODE_SMA_, TaLang::PRICE_CLOSE_, idx);

}

bool TaMovingAverageTimeBar::isInSide(const TaVDirRef& dir, int idx,
		double value) {
	double maI = this->getValue(idx);
	return dir.o()->isSignEquals(value - maI);
}

bool TaMovingAverageTimeBar::isOpenInSide(const TaVDirRef& dir, int idx) {
	return this->isInSide(dir, idx, this->getOpen(idx));
}

bool TaMovingAverageTimeBar::isCloseInSide(const TaVDirRef& dir, int idx) {
	return this->isInSide(dir, idx, this->getClose(idx));
}

bool TaMovingAverageTimeBar::isOCInSide(const TaVDirRef& dir, int idx1, int idx2) {
	bool rt = true;

	for (int i = idx1; i <= idx2; i++) {
		double maI = this->getValue(i);
		double openI = this->getOpen(i);
		double closeI = this->getClose(i);
		if (!dir.o()->isSignEquals(openI - maI) || !dir.o()->isSignEquals(closeI - maI)) {
			rt = false;
			break;
		}
	}

	return rt;

}

bool TaMovingAverageTimeBar::isHorLInSide(const TaVDirRef& dir, int idx) {
	double highI = this->getHigh(idx);
	if (this->isInSide(dir, idx, highI)) {
		return true;
	}
	double lowI = this->getLow(idx);
	if (this->isInSide(dir, idx, lowI)) {
		return true;
	}
	return false;
}

bool TaMovingAverageTimeBar::isHLInSide(const TaVDirRef& dir, int idx) {
	double maI = this->getValue(idx);
	double highI = this->getHigh(idx);
	double lowI = this->getLow(idx);
	if (!dir.o()->isSignEquals(highI - maI) || !dir.o()->isSignEquals(lowI - maI)) {
		return false;
	}
	return true;
}
bool TaMovingAverageTimeBar::isOCInSide(const TaVDirRef& dir, int idx) {
	double maI = this->getValue(idx);
	double openI = this->getOpen(idx);
	double closeI = this->getClose(idx);
	if (!dir.o()->isSignEquals(openI - maI) || !dir.o()->isSignEquals(closeI - maI)) {
		return false;
	}
	return true;
}

bool TaMovingAverageTimeBar::isHLInSide(const TaVDirRef& dir, int idx1, int idx2) {
	bool rt = true;

	for (int i = idx1; i <= idx2; i++) {
		double maI = this->getValue(i);
		double highI = this->getHigh(i);
		double lowI = this->getLow(i);
		if (!dir.o()->isSignEquals(highI - maI) || !dir.o()->isSignEquals(lowI - maI)) {
			rt = false;
			break;
		}
	}

	return rt;

}
