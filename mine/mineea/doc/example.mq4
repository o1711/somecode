#property version "@version@"

#include "../DefineMql.mqh"
#include "../ta/TaTop.mqh"

//--- input parameters
STRING project = "@project@";
STRING version = "@version@";
class TaObject{
public :
	void methodA(){

	}

};
class B : public TaObject{

};
class TaRC {
public:
	int add() {
#ifdef MQL
		this.counter++;
		return this.counter;
#else
		this->counter++;
		return this->counter;
#endif

	}
	int release() {
#ifdef MQL
		this.counter--;
		return this.counter;
#else
		this->counter--;
		return this->counter;
#endif

	}
private:
	int counter;
};

class TaPointer {

public:
	TaPointer(const TaPointer& sr) {
#ifdef MQL
		this.pointer = sr.pointer;
		this.rc = sr.rc;
#else
		this->pointer = sr.pointer;
		this->rc = sr.rc;
#endif
	}
	TaPointer(TaObject* pPointer) {
#ifdef MQL
		this.pointer = pPointer;
		this.rc = new TaRC();
		this.rc.add();
#else
		this->pointer = pPointer;
		this->rc = new TaRC();
		this->rc->add();
#endif

	}
	~TaPointer() {
#ifdef MQL
#else
#endif
		if (this->rc->release() == 0) {
			delete this->pointer;
			delete this->rc;
		}
	}
#ifdef MQL
#else
#endif
	TaObject* r() {
		return this->pointer;
	}
	TaObject* operator -() {
#ifdef MQL
		return this.pointer;
#else
		return this->pointer;
#endif
	}

	TaPointer* operator =(const TaPointer& sref) {
#ifdef MQL
		if (this.rc.release() == 0) {
			delete this.pointer;
			delete this.rc;
		}
		this.pointer = sref.pointer;
		this.rc = sref.rc;

		return GetPointer(this);
#else
		if (this->rc->release() == 0) {
			delete this->pointer;
			delete this->rc;
		}
		this->pointer = sref.pointer;
		this->rc = sref.rc;
		return this;
#endif
	}
private:
	TaObject* pointer;
	TaRC* rc;

};

int OnInit() {

#ifdef MQL

#endif;
	for (int i = 0; i < 10; i++) {
		TaPointer pointer(new B());
		//pointer--methodA();
		pointer-;
	}
	return (TaLang::INIT_SUCCEEDED_);
}

void OnDeinit(const int reason) {
}
