#include "TaConstants.h"


const TaVDirRef TaConstants::DIR_UP = new TaVDir(1);
const TaVDirRef TaConstants::DIR_DOWN = new TaVDir(-1);

const STRING TaConstants::IDR_SUPER_TIME = "super-time";

const double TaConstants::OST_OPEN_ORDER_MAX_RISK = 0.00300;

const double TaConstants::OST_OPEN_ORDER_MIN_RISK = 0.00050;
const double TaConstants::OST_UPDATE_ORDER_BINIFIT_RATE_FOR_STAGE1 = 1.0;

const double TaConstants::OST_MA2_ORIGINAL_LOTS = 0.02;

const double TaConstants::OST_MA2_TAKE_PROFIT_RATE = 3.0;

const double TaConstants::OST_MA2_TAKE_PROFIT_RATE2 = 5.0;

const STRING TaConstants::COM_TICK_HANDLER = "componentTickHandler";

const STRING TaConstants::COM_ACCOUNT_INFOS ="componentAccountInfos";

const STRING TaConstants::COM_TOTAL_LOSS_WEEKLY_RISK_CONTROL = "componentTotalLossWeeklyRiskControl";
const STRING TaConstants::COM_ADVISOR_TICK_HANDLER = "componentAdvisorTickHandler";

const TaColorRef TaConstants::COLOR_RED (new TaColor(255,0,0));
const TaColorRef TaConstants::COLOR_GREEN (new TaColor(0,255,0));
const TaVDirRef TaConstants::getNegative(const TaVDirRef& dir){
	if(dir.o()->isUp()){
		return TaConstants::DIR_DOWN;
	}else{
		return TaConstants::DIR_UP;
	}
}
