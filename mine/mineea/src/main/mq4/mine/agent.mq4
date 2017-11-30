#ifdef MQL
#property version "@version@"
#endif

#include "../ta/TaLib.cpp"
//--- input parameters
STRING project = "@project@";
STRING version = "@version@";

TaAgent * agent;
TaRef ref_agent;
TaJsonConfigFactory * configFactory;
TaRef ref_configFactory;
//+------------------------------------------------------------------+
//| Expert initialization function                                   |
//+------------------------------------------------------------------+
int OnInit() {
	TaSystem::print("onInit");
	STRING configFile = TaLang::__StringConcatenate(project,"-",version,"\\conf\\config.json");

	configFactory = new TaJsonConfigFactory(configFile);
	ref_configFactory = TaRef::ref(configFactory);
	TaConfigRef cRef = configFactory->loadConfig();

	agent = new TaAgent();
	agent->configure(cRef);
	TaRef ref(agent);
	ref_agent = ref;
	agent->start();

	return (TaLang::INIT_SUCCEEDED_);
}

void OnDeinit(const int reason) {
	agent->stop();
	TaSystem::printString("onDeinit");
}

void OnTick() {
//---
	agent->onTick();
}

void OnTimer() {
//---
}

double OnTester() {
//---
	double ret = 0.0;

//---
	return (ret);
}
