
#ifndef TaDouble_MQH
#define TaDouble_MQH
#include "../../system/TaObject.h"
class TaDouble: public TaObject {

public:
	static double parse(STRING pString);

public:
	TaDouble(double pValue);
	virtual ~TaDouble();
	double doubleValue();
private:
	double value;
};

#endif
