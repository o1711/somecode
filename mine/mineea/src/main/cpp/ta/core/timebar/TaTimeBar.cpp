#include "TaTimeBar.h"

TaTimeBar::TaTimeBar() :
		TaContainerAwareObject() {

}

TaTimeBar::~TaTimeBar() {

}
void TaTimeBar::getTime(int idx,TaDateTime* dt){
	TaLang::getTime(idx,dt);
}
double TaTimeBar::getHigh() {
	return TaLang::getHigh(0);
}

double TaTimeBar::getLow() {
	return TaLang::getLow(0);
}

double TaTimeBar::getOpen() {
	return TaLang::getOpen(0);
}

double TaTimeBar::getClose() {
	return TaLang::getClose(0);
}
double TaTimeBar::getHigh(int idx) {
	return TaLang::getHigh(idx);
}

double TaTimeBar::getLow(int idx) {
	return TaLang::getLow(idx);
}

double TaTimeBar::getOpen(int idx) {
	return TaLang::getOpen(idx);
}

double TaTimeBar::getClose(int idx) {
	return TaLang::getClose(idx);
}

long TaTimeBar::getVolume(int idx) {
	long rt = TaLang::getVolume(idx);
	return rt;

}

double TaTimeBar::getHLDelta(int idx) {
	return this->getHigh(idx) - this->getLow(idx);
}

double TaTimeBar::getHLDeltaAbs(int idx) {
	return TaLang::__MathAbs(getHLDelta(idx));
}

