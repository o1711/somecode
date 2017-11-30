#ifndef TaConstants_MQH
#define TaConstants_MQH

#include "../system/TaLang.h"
#include "lang/TaVDir.h"

class TaConstants {
public:

	static const TaVDirRef DIR_UP;
	static const TaVDirRef DIR_DOWN;

	static const STRING IDR_SUPER_TIME;

	static const double OST_MA2_TAKE_PROFIT_RATE;

	static const double OST_MA2_TAKE_PROFIT_RATE2;

	static const double OST_MA2_ORIGINAL_LOTS;

	static const double OST_OPEN_ORDER_MAX_RISK;

	static const double OST_OPEN_ORDER_MIN_RISK;

	static const double OST_UPDATE_ORDER_BINIFIT_RATE_FOR_STAGE1;

	static const STRING COM_TICK_HANDLER;
	static const STRING COM_ACCOUNT_INFOS;
	static const STRING COM_ADVISOR_TICK_HANDLER;
	static const STRING COM_TOTAL_LOSS_WEEKLY_RISK_CONTROL;

	static const TaVDirRef getNegative(const TaVDirRef& dir);

	static const TaColorRef COLOR_RED;
	static const TaColorRef COLOR_GREEN;
};

#endif
