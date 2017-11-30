#include "TaDouble.h"
TaDouble::TaDouble(double pValue) {
	this->value = pValue;
}

TaDouble::~TaDouble() {

}

double TaDouble::doubleValue() {
	return this->value;
}

double TaDouble::parse(STRING pString){
	return TaLang::__StringToDouble(pString);
}
