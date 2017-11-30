#ifndef TaVDir_MQH
#define TaVDir_MQH


#include "TaDateTime.h"
#include "../../system/TaRef.h"

class TaVDir : public TaObject{
public:
	short value;
public:

	TaVDir(short pValue);
	virtual ~TaVDir();
	bool isUp() const;
	bool isDown() const;
	bool isSignEquals(double vector) const;
	bool isAllSignEquals(double& vectors[]) const;
	double multiple(double vector) const;
};

class TaVDirRef: public TaRef {

public:
	static TaVDirRef Null(){
		TaVDirRef ref;
		return ref;
	}

public:
	TaVDirRef(){}
	TaVDirRef(const TaRef& sr):TaRef(sr){}
	TaVDirRef(TaVDir* pPointer):TaRef(pPointer){}
	TaVDir* o() const {
		return this->pointer;
	}
};
#endif
