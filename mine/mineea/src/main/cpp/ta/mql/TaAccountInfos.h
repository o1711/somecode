
#ifndef TaAccountInfos_MQH
#define TaAccountInfos_MQH
#include "../system/TaObject.h"
class TaAccountInfos: public TaObject {

public:
	TaAccountInfos();
	virtual ~TaAccountInfos();
	int getAccountId();
	void setReserved(double reserved);
	double getReserved();
	double getEquity();
	double getBalance();
	bool isDemo();
protected:
	double reserved;

};

TaAccountInfos::TaAccountInfos() {

}

TaAccountInfos::~TaAccountInfos() {

}

double TaAccountInfos::getBalance(){
	return TaLang::__AccountBalance();
}

bool TaAccountInfos::isDemo(){
	return TaLang::__IsDemo();
}

void TaAccountInfos::setReserved(double pReserved) {
	this->reserved = pReserved;
}
double TaAccountInfos::getEquity(){
	return TaLang::__AccountEquity();
}
int TaAccountInfos::getAccountId(){
	return TaLang::getAccountNumber();
}
double TaAccountInfos::getReserved() {
	return this->reserved;

}
#endif
