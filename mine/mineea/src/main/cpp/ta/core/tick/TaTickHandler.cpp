#include "TaTickHandler.h"

TaTickHandler::TaTickHandler(STRING pName):TaContainerAwareObject(){
	this->active = true;
	this->name = pName;
}

TaTickHandler::~TaTickHandler(){
}

bool TaTickHandler::isActive(){
   return active;
}

void TaTickHandler::onTick(){
	if(!this->active){
		return;
	}
	this->onTickInternal();
}
void TaTickHandler::onTickInternal(){

}

