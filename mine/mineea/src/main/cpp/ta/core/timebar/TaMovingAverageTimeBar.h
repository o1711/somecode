#ifndef TaMovingAverageTimeBar_MQH
#define TaMovingAverageTimeBar_MQH

#include "TaTimeBar.h"

class TaMovingAverageTimeBar: public TaTimeBar {

protected:

	int days;

public:

	TaMovingAverageTimeBar(int days);
	virtual ~TaMovingAverageTimeBar();

	double getValue(int idx);


	bool isOCInSide(const TaVDirRef& dir, int idx1, int idx2);
	bool isOCInSide(const TaVDirRef& dir, int idx);
	bool isHLInSide(const TaVDirRef& dir, int idx1, int idx2);
	bool isHLInSide(const TaVDirRef& dir, int idx1);
	bool isHorLInSide(const TaVDirRef& dir, int idx);
	bool isInSide(const TaVDirRef& dir, int idx, double value);
	bool isOpenInSide(const TaVDirRef& dir, int idx);
	bool isCloseInSide(const TaVDirRef& dir, int idx);

};
#endif
