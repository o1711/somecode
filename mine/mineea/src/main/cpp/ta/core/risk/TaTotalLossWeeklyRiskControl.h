
#ifndef TA_TOTAL_LOSS_WEEKLY_RISK_CONTROL_MQH
#define TA_TOTAL_LOSS_WEEKLY_RISK_CONTROL_MQH
#include "TaRiskControl.h"
#include "../../mql/TaAccountInfos.h"
#include "../../common/log/TaLoggerFactory.h"

class TaTotalLossWeeklyRiskControl: public TaRiskControl {

public:
	static TaLoggerRef LOG;
public:
	TaTotalLossWeeklyRiskControl();
	virtual ~TaTotalLossWeeklyRiskControl();
	virtual bool isOrderOpenAllowed(TaOrderOpeningAction * oA);
	virtual void setMaxWeekLossRate(double pRate);
	virtual void setContainer(TaContainerRef& cref);

protected:

	int lastSundayOfYear;

	double lastAccountEquity;

	double weekLossLimit;

	double maxWeekLossRate;

	TaAccountInfos* accountInfos;

	TaRef ref_accountInfos;

};
#endif /*TA_TOTAL_LOSS_WEEKLY_RISK_CONTROL_MQH*/
