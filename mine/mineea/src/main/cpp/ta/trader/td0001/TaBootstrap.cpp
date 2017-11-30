
#include "TaBootstrap.h"

TaBootstrap::TaBootstrap(){

}

TaBootstrap::~TaBootstrap(){

}

TaDriverRef TaBootstrap::load(STRING configFile){

	TaDriverRef rt(new TaTd0001Driver(configFile));
	rt.o()->start();
	return rt;
	
}
