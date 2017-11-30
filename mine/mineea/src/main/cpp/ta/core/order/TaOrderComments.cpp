#include "TaOrderComments.h"

TaOrderComments::TaOrderComments() {

}

TaOrderComments::TaOrderComments(STRING pText) {
	this->text = pText;
}

TaOrderComments::~TaOrderComments() {

}

bool TaOrderComments::isFrom(){
	int idx = TaLang::__StringFind(this->text,("from"),0);
	if(idx ==0){
		return true;
	}
	return false;
}

int TaOrderComments::getFrom(){
	STRING idS = TaLang::__StringSubstr(this->text,TaLang::__StringLen(("from #")));
	return TaLang::__StringToInteger(idS);
}


double TaOrderComments::getOriginalRisk() {
	this->makeSureParsed();
	return this->originalRisk;
}

void TaOrderComments::setOriginalRisk(double oRisk) {
	this->originalRisk = oRisk;
}

void TaOrderComments::makeSureParsed() {
	if (this->parsed) {
		return;
	}
	this->originalRisk = TaLang::__StringToDouble(this->text);
}

STRING TaOrderComments::to() {

	return TaLang::__DoubleToString(this->originalRisk);
}

