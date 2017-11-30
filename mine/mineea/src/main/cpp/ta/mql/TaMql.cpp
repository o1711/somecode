#include "TaMql.h"

TaMql::TaMql(){
	//TaLang::__Print("TaMql");
	this->infos = new TaMqlInfos();
	this->ref_infos = TaRef::ref(this->infos);
	this->accountInfos = this->ref(new TaAccountInfos());

}

TaMql::~TaMql(){
	//TaLang::__Print("~TaMql");
}

TaMqlInfos* TaMql::getInfos(){
	return this->infos;
}

TaAccountInfos* TaMql::getAccountInfos(){
	return this->accountInfos;
}

