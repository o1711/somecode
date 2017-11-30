#ifndef TaTotalLossDailyRiskControl_MQH
#define TaTotalLossDailyRiskControl_MQH

#include "TaRiskControl.h"

class TaTotalLossDailyRiskControl: public TaRiskControl {

	int lastDayOfYear;

	double lastAccountEquity;

	double maxDayLossAllowed;

public:
	TaTotalLossDailyRiskControl();
	virtual ~TaTotalLossDailyRiskControl();
	virtual bool isOrderOpenAllowed(TaOrderOpeningAction * oA);

};
#endif
