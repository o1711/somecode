
#include "TaInteger.h"

TaInteger::TaInteger(int pValue) {
	this->value = pValue;
}

TaInteger::~TaInteger() {

}

int TaInteger::intValue() {
	return this->value;
}

int TaInteger::parse(STRING pString){
	return TaLang::__StringToInteger(pString);
}
