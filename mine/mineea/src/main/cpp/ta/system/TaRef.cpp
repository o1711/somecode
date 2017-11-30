#include "TaRef.h"

int TaRef_Counter::nextId = 0;

TaRef_Counter::TaRef_Counter() :
		counter(0) {
	this->id = TaRef_Counter::nextId++;
	//TaLang::__Print("RC,id:", id);
}
TaRef_Counter::~TaRef_Counter() {
	//TaLang::__Print("~RC,id:", id);
}

int TaRef_Counter::add() {
	this->counter++;
	return this->counter;

}
int TaRef_Counter::release() {
	this->counter--;
	return this->counter;

}

int TaRef::nextId = 0;

TaRef::TaRef() :
		pointer(NULL), rc(NULL) {

	this->id = nextId++;
	//TaLang::__Print("TaReference(),id:", id);
	//this->rc = new RC();
}
TaRef::TaRef(const TaRef& sr) :
		pointer(sr.pointer), rc(sr.rc) {
	this->id = nextId++;
	//TaLang::__Print("TaReference(&),id:", id);
	if (this->pointer != NULL) {
		this->rc->add();
	}

}
TaRef::TaRef(TaObject* pPointer) :
		pointer(pPointer), rc(NULL) {
	this->id = nextId++;
	//TaLang::__Print("TaReference(*),id:", id);
	if (pPointer != NULL) {
		this->rc = new TaRef_Counter();
		this->rc->add();
	}
}
TaRef::~TaRef() {
	//TaLang::__Print("~TaReference(),id:", id);
	//TaLang::__Print("~TaReference(),counter:", this->counter());
	if (this->pointer != NULL) {

		if (this->rc->release() == 0) {
			int rcId = this->rc->id;
			delete this->pointer;
			this->pointer = NULL;
			delete this->rc;
			this->rc = NULL;
			//TaLang::__Print("rc = 0 in ~TaReference(),rc.id:", rcId);
		}
	}
}

bool TaRef::isNull(){
	return this->pointer == NULL;
}
int TaRef::counter() {
	if (this->pointer == NULL) {
		return 0;
	}
	return this->rc->counter;
}
STRING TaRef::toString() {
	if (this->rc == NULL) {
		//TaLang::__Print("toString,rc == NULL");
	} else {
		//TaLang::__Print("toString,counter:", this->counter());
	}
	return "TaReference.toString()";
}

TaRef TaRef::ref(TaObject* obj){
	TaRef rt(obj);
	return rt;
}

TaObject* TaRef::obj() {
#ifdef MQL
	return GetPointer(this->pointer);
#else
	return this->pointer;
#endif
}

TaRef* TaRef::operator =(const TaRef& sref) {
	//TaLang::__Print("operator =");
	if (this->pointer != NULL) {

		if (this->rc->release() == 0) {
			delete this->pointer;
			this->pointer = NULL;
			delete this->rc;
			this->rc = NULL;
			//TaLang::__Print("rc = 0 in operator=");
		}
	}
	this->pointer = sref.pointer;
	this->rc = sref.rc;
	if (this->pointer != NULL) {
		this->rc->add();
	}

#ifdef MQL
	return GetPointer(this);
#else
	return this;
#endif
}

