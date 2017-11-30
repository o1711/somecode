#include "TaAccountInfos.h"

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
double TaAccountInfos::getReserved() {
	return this->reserved;

}

