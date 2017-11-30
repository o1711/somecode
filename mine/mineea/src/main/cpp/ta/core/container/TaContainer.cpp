#include "TaContainer.h"

TaLoggerRef TaContainer::LOG = TaLoggerFactory::getLogger("ta->core->container->TaContainer");

//Chart Context 
TaContainer::TaContainer() :
		TaObject() {
	this->lastBarTime = NULL;
	this->componentMap = new TaStringRefMap();
	this->notifier = new TaNotifier();
}

TaContainer::~TaContainer() {
	delete this->componentMap;
	delete this->notifier;
}

TaRef TaContainer::getComponent(STRING id) {
	TaRef rt = this->componentMap->getRef(id);
	if (rt.isNull()) {
		LOG.o()->warn(TaLang::__StringConcatenate("no component found with id:", id));
	}
	return rt;
}
void TaContainer::destroy(){
	this->componentMap->clear(true);
}
void TaContainer::addComponent(const STRING id, TaRef& component) {
	LOG.o()->info(TaLang::__StringConcatenate(("add component:"), id));
	this->componentMap->put(id, component);
}

TaNotifier* TaContainer::getNotifier() {
	return this->notifier;
}
