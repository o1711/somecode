#ifndef TaMa2OrderOpenner_MQH
#define TaMa2OrderOpenner_MQH


#include "../../common/lang/TaVDir.h"
#include "../../core/container/TaContainerAwareObject.h"
#include "../../core/advisor/support/TaBaseOrderOpenner.h"
#include "../../core/timebar/TaTwoMovingAverageTimeBar.h"

class TaMa2OrderOpenner: public TaBaseOrderOpenner {

public:
	TaMa2OrderOpenner(int strategy,STRING pName, const TaVDirRef& dir);
	virtual ~TaMa2OrderOpenner();

	virtual bool tryOpenOrder(TaOrderOpeningActionRef& pA);

	void doOpenOrder(TaOrderOpeningActionRef& pA, int startCrossingIndex,
			int crossedIndex);

	int getStartIndex();
	int getCrossedIndex(int startIndex);
	int getMacdFirstMachedIndex(int crossedIndex);
	bool isHLDeltaOk();
	bool isMacdMatched(int idx);
	bool isAllBeforeCrossingOpenCloseMatch(int startCrossingIndex);
	virtual double orderLots();
	virtual double orderRisk(double price, int startIndex);
	void setOrderLots(double lots);

protected:

	TaTwoMovingAverageTimeBar * twoMa;

	int maxBarsForCrossing;

	int minBarsBeforeCrossing;

	int maxBarsForWaitingMacd;

private:

	TaDateTime* lastOrderTimeBar;

	double orderLots_;

};
#endif
