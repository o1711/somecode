#ifndef TA_LOGGER_MQH_
#define TA_LOGGER_MQH_

class TaLogger: public TaObject {

public:
	static const int INFO;
	static const int DEBUG;
	static const int TRACE;
	static const int ERROR;
	static const int WARN;
	static STRING getLevelName(int pLevel);
private:
	STRING name;
	int level;
	void log(int LEVEL, STRING message);
public:
	TaLogger(STRING name);
	virtual ~TaLogger();
	//TODO void error(TaObject* obj);
	void error(STRING message);
	void warn(STRING message);
	void info(STRING message);
	void debug(STRING message);
	void debug(TaRef& message);
	void debug(TaObject* message);
	void trace(STRING message);
};
//
class TaLoggerRef: public TaRef {
public:
	static TaLoggerRef Null(){
		TaLoggerRef ref;
		return ref;
	}
public:
	TaLoggerRef(){}
	TaLoggerRef(const TaRef& sr):TaRef(sr){}
	TaLoggerRef(TaLogger* pPointer):TaRef(pPointer){}
	TaLogger* o() const {return this->pointer;}
};
#endif /*TA_LOGGER_MQH_*/
