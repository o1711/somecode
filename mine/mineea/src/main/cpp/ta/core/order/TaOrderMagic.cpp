

#include "TaOrderMagic.h"
const int TaOrderMagic::MAX_STRATEGY = 100000;

TaOrderMagic::TaOrderMagic() {

}

TaOrderMagic::TaOrderMagic(int magic) {
	this->strategy = magic;
}


TaOrderMagic::~TaOrderMagic() {

}

void TaOrderMagic::setStrategy(int pStrategy){
	this->strategy = pStrategy;
}

int TaOrderMagic::encode(){
	return this->strategy;
}
