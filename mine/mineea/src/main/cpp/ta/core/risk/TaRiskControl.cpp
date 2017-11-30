#include "TaRiskControl.h"


TaRiskControl::TaRiskControl():TaContainerAwareObject(){

}
TaRiskControl::~TaRiskControl(){

}
bool TaRiskControl::isOrderOpenAllowed(TaOrderOpeningAction * oA) {
	return false;
}

