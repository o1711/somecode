#ifndef TaDateTime_MQH
#define TaDateTime_MQH
#include "../../system/TaObject.h"

class TaDateTime : TaObject{


public:
	static TaDateTime* getNull();
	void set(int y, int m, int d, int h, int mi, int s);
	void set(TaDateTime* dt);
	bool isEquals(TaDateTime* obj);
	STRING toFormatedString();

protected:
	int year;
	int month;
	int day;
	int hour;
	int minites;
	int seconds;
};
#endif
