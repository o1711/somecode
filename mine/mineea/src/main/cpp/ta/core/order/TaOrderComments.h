#ifndef TaOrderComments_MQH
#define TaOrderComments_MQH

#include "../../system/TaLang.h"

class TaOrderComments {

public:
	TaOrderComments();
	TaOrderComments(STRING text);
	virtual ~TaOrderComments();

	double getOriginalRisk();
	void setOriginalRisk(double oRisk);
	STRING to();
	bool isFrom();
	int getFrom();

protected:
	STRING text;
	bool parsed;
	double originalRisk;
private:
	void makeSureParsed();
};
#endif
