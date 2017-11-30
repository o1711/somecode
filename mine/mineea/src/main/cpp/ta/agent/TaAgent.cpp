#include "TaAgent.h"

TaLoggerRef TaAgent::LOG(TaLoggerFactory::getLogger("ta.agent.TaAgent"));

TaAgent::TaAgent() {
	this->webClient = new TaWebClient();
	this->ref(this->webClient);
	this->jsonParser = new TaJsonParser();
	this->ref(this->jsonParser);

	this->timeBar = new TaTimeBar();
	this->ref(this->timeBar);

	this->accountInfos = this->ref(new TaAccountInfos());
}

TaAgent::~TaAgent() {

}
void TaAgent::configure(TaConfigRef& config) {
	STRING host = config.o()->getProperty("host");
	STRING uri = config.o()->getProperty("uri");

	this->url = "http://" + host + uri;

}
TaJsonObjectRef TaAgent::request(TaJsonObjectRef& obj) {
	TaWebRequestRef req(new TaWebRequest());
	req.o()->setUrl(this->url);
	req.o()->setTimeout(0);
	req.o()->setBody(obj.o()->toJsonString());

	TaWebResponseRef res = webClient->request(req);
	int rc = res.o()->getReturnCode();
	if (rc == -1) {

	}
	//TaSystem::print("returnCode:");
	//TaSystem::print(rc);
	STRING rst = res.o()->getBody();
	//TaSystem::print(rst);
	TaJsonObjectRef rt = this->jsonParser->parseJsonString(rst);
	return rt;
}
void TaAgent::start() {
	TaJsonObject* obj = new TaJsonObject();
	obj->setProperty("handler", "init");

	{
		TaJsonObject * objA = this->createAccountData();
		obj->setProperty("accountInfo", objA);
	}

	TaJsonObjectRef ref(obj);

	TaJsonObjectRef rstR = this->request(ref);

	this->sessionId = rstR.o()->getPropertyAsString("sessionId");

	//upload 100 timebar.
	this->uploadTimeBars();

}
TaJsonObject* TaAgent::createAccountData() {
	TaJsonObject* objA = new TaJsonObject();
	objA->setProperty("isDemo", this->accountInfos->isDemo() ? "Y" : "N");
	objA->setProperty("accountId", this->accountInfos->getAccountId() + "");
	objA->setProperty("equity", this->accountInfos->getEquity() + "");
	return objA;
}
void TaAgent::uploadTimeBars() {
	TaJsonObject* obj = new TaJsonObject();
	obj->setProperty("sessionId", this->sessionId); //
	obj->setProperty("handler", "timebars");
	TaJsonArray* arr = new TaJsonArray();
	TaDateTime* tmpDateTime = new TaDateTime();
	TaRef ref1(tmpDateTime);
	for (int i = 0; i < 200; i++) {
		TaJsonObject * oI = new TaJsonObject();
		this->timeBar->getTime(i, tmpDateTime);
		oI->setProperty("time", tmpDateTime->toFormatedString()); //
		oI->setProperty("high", this->timeBar->getHigh(i) + "");
		oI->setProperty("low", this->timeBar->getLow(i) + "");
		oI->setProperty("open", this->timeBar->getOpen(i) + "");
		oI->setProperty("close", this->timeBar->getClose(i) + "");
		oI->setProperty("volume", this->timeBar->getVolume(i) + "");
		arr->add(oI);
	}
	obj->setProperty("timebarArray", arr);
	TaJsonObjectRef ref(obj);
	ref = this->request(ref);

}

void TaAgent::stop() {
	TaJsonObject* obj = new TaJsonObject();
	obj->setProperty("sessionId", this->sessionId); //
	obj->setProperty("handler", "stop");

	TaJsonObjectRef ref(obj);
	ref = this->request(ref);
}

void TaAgent::onTick() {

	TaJsonObject* obj = new TaJsonObject();
	obj->setProperty("sessionId", this->sessionId); //
	obj->setProperty("handler", "tick");
	TaDateTime* tmpDateTime = new TaDateTime();
	TaRef ref1(tmpDateTime);

	this->timeBar->getTime(0, tmpDateTime); //

	obj->setProperty("time", tmpDateTime->toFormatedString()); //
	obj->setProperty("high", this->timeBar->getHigh() + "");
	obj->setProperty("open", this->timeBar->getOpen() + "");
	obj->setProperty("close", this->timeBar->getClose() + "");
	obj->setProperty("low", this->timeBar->getLow() + "");
	obj->setProperty("volume", this->timeBar->getVolume(0) + "");
	obj->setProperty("ask", TaLang::getAsk() + "");
	obj->setProperty("bid", TaLang::getBid() + "");
	//obj->setProperty("spread",TaLang::getSpread()+"");
	TaJsonObject* objA = this->createAccountData();
	obj->setProperty("accountInfo", objA);

	//TODO orders.

	TaJsonObjectRef ref(obj);
	ref = this->request(ref);
}

