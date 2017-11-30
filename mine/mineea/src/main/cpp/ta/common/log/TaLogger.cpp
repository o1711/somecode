#include "TaLogger.h"

const int TaLogger::ERROR = 2;
const int TaLogger::WARN = 3;
const int TaLogger::INFO = 4;
const int TaLogger::DEBUG = 5;
const int TaLogger::TRACE = 6;

TaLogger::TaLogger(STRING pName) {
	this->name = pName;
	this->level = INFO;
}

TaLogger::~TaLogger() {

}
STRING TaLogger::getLevelName(int pLevel) {
	switch (pLevel) {
	case 2:
		return "error";
		break;
	case 3:
		return "warn ";
		break;
	case 4:
		return "info ";
		break;
	case 5:
		return "debug";
		break;
	case 6:
		return "trace";
		break;

	}
	return "unknow";
}

void TaLogger::error(STRING message) {
	log(ERROR, message);
}
void TaLogger::warn(STRING message) {
	log(WARN, message);
}

void TaLogger::info(STRING message) {
	log(INFO, message);
}
void TaLogger::debug(TaRef& ref){
	this->debug(ref.obj()->toString());
}
void TaLogger::debug(TaObject* obj){
	this->debug(obj->toString());
}

void TaLogger::debug(STRING message) {
	log(DEBUG, message);
}
void TaLogger::trace(STRING message) {
	log(TRACE, message);
}

void TaLogger::log(int pLevel, STRING message) {
	TaLang::__Print(
			TaLang::__StringConcatenate(
					TaLang::__StringConcatenate("[", getLevelName(pLevel),
							"] "),
					TaLang::__StringConcatenate(this->name, " ", message)));
}

