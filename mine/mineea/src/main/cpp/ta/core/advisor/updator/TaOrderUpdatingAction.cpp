#include "TaOrderUpdatingAction.h"

TaOrderUpdatingAction::TaOrderUpdatingAction(int pOrderId){
	this->orderId = pOrderId;
}

TaOrderUpdatingAction::~TaOrderUpdatingAction(){
}

int TaOrderUpdatingAction::getOrderId(){
	return orderId;
}
