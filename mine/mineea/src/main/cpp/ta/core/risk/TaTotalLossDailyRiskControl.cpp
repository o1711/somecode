#include "TaTotalLossDailyRiskControl.h"

TaTotalLossDailyRiskControl::TaTotalLossDailyRiskControl() :
		TaRiskControl(ct) {
	this->maxDayLossAllowed = 5.0;
	this->lastDayOfYear = -1;
}

TaTotalLossDailyRiskControl::~TaTotalLossDailyRiskControl() {

}

bool TaTotalLossDailyRiskControl::isOrderOpenAllowed(
		TaOrderOpeningAction * oA) {
	int dayOfY = TaLang::__DayOfYear();

	if (this->lastDayOfYear != dayOfY) {
		this->lastDayOfYear = dayOfY;
		lastAccountEquity = TaLang::__AccountEquity();
	}
	////this->doLog("lastDayOfYear:",IntegerTo(this->lastDayOfYear));
	double ae = TaLang::__AccountEquity();
	if (lastAccountEquity - ae > maxDayLossAllowed) {

		return false;
	}
	return true;
}

