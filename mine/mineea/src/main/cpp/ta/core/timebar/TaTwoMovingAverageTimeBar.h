#ifndef TaTwoMovingAverageTimeBar_MQH
#define TaTwoMovingAverageTimeBar_MQH

#include "TaTimeBar.h"
#include "TaMovingAverageTimeBar.h"

class TaTwoMovingAverageTimeBar: public TaTimeBar {

protected:
	TaMovingAverageTimeBar * ma1;
	TaMovingAverageTimeBar * ma2;
public:

	TaTwoMovingAverageTimeBar(int days1, int days2);
	virtual ~TaTwoMovingAverageTimeBar();
	double getValue1(int idx);
	double getValue2(int idx);
	double getMidValue(int idx);
	bool isCloseInSideOfAllMa(const TaVDirRef& dir, int idx);
	bool isOpenInSideOfAllMa(const TaVDirRef& dir, int idx);

	bool isOCInSideOfAllMa(const TaVDirRef& dir, int idx1, int idx2);

	bool isAllOpenCloseInSideOfSlowMa(const TaVDirRef& dir, int idx1, int idx2);

	bool isOCInSideOfAllMa(const TaVDirRef& dir, int idx);

	bool isHLInSideOfAllMa(const TaVDirRef& dir, int idx1, int idx2);

	bool isHLInSideOfAllMa(const TaVDirRef& dir, int idx);

	bool isHorLInSideOfAllMa(const TaVDirRef& dir, int idx);

	bool isInSideOfAllMa(const TaVDirRef& dir, int idx, double value);

};
#endif
