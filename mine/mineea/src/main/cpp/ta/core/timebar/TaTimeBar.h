#ifndef TaTimeBar_MQH
#define TaTimeBar_MQH


#include "../container/TaContainerAwareObject.h"

class TaTimeBar: public TaContainerAwareObject {
protected:

public:
	TaTimeBar();
	virtual ~TaTimeBar();
	virtual double getHigh();
	virtual double getLow();
	virtual double getOpen();
	virtual double getClose();
	virtual void getTime(int idx, TaDateTime* dt);
	virtual double getHigh(int idx);
	virtual double getLow(int idx);
	virtual double getOpen(int idx);
	virtual double getClose(int idx);
	virtual long getVolume(int idx);
	virtual long getMaxVolume(int idxFrom, int idxTo);
	virtual int getTheMaxVolumeIndex(int idxFrom, int idxTo);
	virtual double getPeek(const TaVDirRef& dir, int idx1, int idx2);
	virtual double getHLDelta(int idx);
	virtual double getHLDeltaAbs(int idx);
};
#endif
