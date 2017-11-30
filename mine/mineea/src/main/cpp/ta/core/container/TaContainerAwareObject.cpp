
#include "TaContainerAwareObject.h"

TaContainerAwareObject::TaContainerAwareObject(){

}

TaContainerAwareObject::~TaContainerAwareObject(){

}
void TaContainerAwareObject::setContainer(TaContainerRef& cref){
//TODO throw exception?
}
void TaContainerAwareObject::initLogger(STRING pLoggerName){
	 this->logger = TaLoggerFactory::getLogger(pLoggerName);
}
