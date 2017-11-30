#include "TaJsonConfigFactory.h"
#include "../json/TaJsonParser.h"
TaJsonConfigFactory::TaJsonConfigFactory(STRING configFile) :
		TaConfigFactory(configFile) {
	this->initLogger("TaJsonConfigFactory");
}

TaJsonConfigFactory::~TaJsonConfigFactory() {

}
STRING TaJsonConfigFactory::readFile(STRING resource) {
	TaStringBufferRef sb1(new TaStringBuffer());
	TaRef ref(sb1);
	this->logger.o()->info(sb1.o()->append("readFile:")->append(resource)->toString());
	int file = TaLang::__FileOpen(resource,
			TaLang::FILE_CSV_ | TaLang::FILE_READ_, '|');
	if (file == TaLang::INVALID_HANDLE_) {
		if (TaLang::__GetLastError() == 4103) {
//			TaLang::__Print(
//					TaLang::__StringConcatenate("TaJsonConfigFactory::readFile,file not found:", resource));
		}
//		TaLang::__Print(
//				TaLang::__StringConcatenate("TaJsonConfigFactory::readFile,error:",
//						TaLang::__IntegerToString(TaLang::__GetLastError())));
//
		TaStringBufferRef sbx(new TaStringBuffer());
		TaRef ref(sbx);
		sbx.o()->append("TaJsonConfigFactory::readFile,read file error,")->append(
				TaLang::__GetLastError())->append(resource);
		this->logger.o()->error(sbx.o()->toString());
		return NULL;
	}
	TaStringBufferRef sb(new TaStringBuffer());
	while (TaLang::__FileIsEnding(file) == false) {

		STRING str = TaLang::__FileReadString(file);
		sb.o()->append(str);
	} //end while
	TaLang::__FileClose(file);
	STRING rt = sb.o()->toString();
	return rt;
}

TaJsonValueRef TaJsonConfigFactory::readFileAsJson(STRING resource) {
	STRING text = this->readFile(resource);
	if (text == NULL) {
		this->logger.o()->error("text not read.");
	}
	this->logger.o()->debug(text); //
	TaRef ref(new TaJsonParser());
	TaJsonParser* parser = ref.obj();
	//TaJsonParser* parser = new TaJsonParser();
	TaJsonValueRef rt = parser->parseJsonString(text);
	//this->logger.o()->debug(rt->toString());
	//delete parser;
	return rt;
}

void TaJsonConfigFactory::convertFieldToConfig(TaConfig* parent, STRING key,
		TaJsonValueRef& jsonR) {

	TaJsonValue* json = jsonR.o();
	if (json->isString()) { //set property to parent
		parent->setProperty(key, ((TaJsonString*) json)->getStringValue());
	} else if (json->isArray()) { //add child to parent
		TaConfigRef childRef(new TaConfig(key));
		TaJsonArray* jsonArr = (TaJsonArray*) json;
		for (int i = 0; i < jsonArr->getSize(); i++) {
			TaJsonValueRef jsonI = jsonArr->get(i);
			if (!jsonI.o()->isObject()) {
				//ignore none object in array.
				continue;
			}
			TaConfig* grandSonI = this->convertToConfig(NULL,jsonI);
			TaConfigRef grandSonIRef(grandSonI);
			childRef.o()->addChild(grandSonIRef);
		}
		parent->addChild(childRef);
	} else if (json->isObject()) {
		TaConfig* child = this->convertToConfig(key, jsonR);
		TaConfigRef childRef(child);
		parent->addChild(childRef);
	}
}

TaConfig* TaJsonConfigFactory::convertToConfig(STRING type,
		TaJsonValueRef& jsonR) {

	//this->logger.o()->debug(json->toString());
	TaJsonObject* json = jsonR.o();
	if (type == NULL) {
		type = json->getPropertyAsString("type");
	}
	TaConfig * rt = new TaConfig(type);
	TaRefList* pNameList = json->getPropertyNameList();
	this->logger.o()->debug(pNameList->toString());
	for (int i = 0; i < pNameList->getSize(); i++) {
		TaString* nameObj = pNameList->get(i).obj();
		STRING key = nameObj->stringValue;
		TaJsonValueRef pValue = json->getProperty(key);
		this->convertFieldToConfig(rt, key, pValue);
	}
	return rt;

}
TaRef TaJsonConfigFactory::loadConfig() {
	STRING resource = this->configFile;
	this->logger.o()->info("loadConfig:");
	this->logger.o()->info(resource);
	if(resource == NULL){
		this->logger.o()->error("resource is NULL");
	}
	TaJsonValueRef jsonR =	this->readFileAsJson(resource);

	if (jsonR.isNull()) {
		this->logger.o()->error("failed to load resource.");
	}

	TaConfig* rt = NULL;

	rt = this->convertToConfig(NULL, jsonR);
	this->logger.o()->debug(rt->toString());

	TaRef ref(rt);
	return ref;
}
