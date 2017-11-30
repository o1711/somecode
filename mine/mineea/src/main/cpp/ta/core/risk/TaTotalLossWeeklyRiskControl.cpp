
#include "TaTotalLossWeeklyRiskControl.h"

TaLoggerRef TaTotalLossWeeklyRiskControl::LOG = TaLoggerFactory::getLogger(
		("ta->core->risk->TaTotalLossWeeklyRiskControl"));


TaTotalLossWeeklyRiskControl::TaTotalLossWeeklyRiskControl() :
		TaRiskControl() {
	this->lastSundayOfYear = -8;

}

TaTotalLossWeeklyRiskControl::~TaTotalLossWeeklyRiskControl() {

}

void TaTotalLossWeeklyRiskControl::setContainer(TaContainerRef& cref){

	this->ref_accountInfos = cref.o()->getComponent(TaConstants::COM_ACCOUNT_INFOS);
	this->accountInfos = this->ref_accountInfos.obj();
}

void TaTotalLossWeeklyRiskControl::setMaxWeekLossRate(double pRate) {
	this->maxWeekLossRate = pRate;
}

bool TaTotalLossWeeklyRiskControl::isOrderOpenAllowed(
		TaOrderOpeningAction * oA) {

	int dOfY = TaLang::__DayOfYear();
	int dOfW = TaLang::__DayOfWeek();
	int theSunday = dOfY - dOfW;

	if (theSunday != this->lastSundayOfYear) {
		this->lastSundayOfYear = theSunday;
		lastAccountEquity = this->accountInfos->getEquity()
				- this->accountInfos->getReserved();
		weekLossLimit = lastAccountEquity * maxWeekLossRate;
	}
	////this->doLog("lastWeekOfYear:",IntegerTo(this->lastWeekOfYear));
	double ae = this->accountInfos->getEquity()
			- this->accountInfos->getReserved();
	double loss = lastAccountEquity - ae;
	if (loss > weekLossLimit) {
		LOG.o()->warn(
				TaLang::__StringConcatenate("max weekly loss(",
						TaLang::__DoubleToString(loss),
						(") exceeded the max limit("),
						TaLang::__StringConcatenate(
								TaLang::__DoubleToString(this->weekLossLimit),
								")", ("cannot open order for this week->"))));
		return false;
	}
	return true;
}

