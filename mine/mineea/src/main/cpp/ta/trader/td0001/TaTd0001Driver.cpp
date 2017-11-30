#include "TaTd0001Driver.h"
TaLoggerRef TaTd0001Driver::LOG = TaLoggerFactory::getLogger(
		("ta->trader->td0001.TaTd0001Driver"));

TaTd0001Driver::TaTd0001Driver(STRING pConfigFile) :
		TaDriver(pConfigFile) {

}

TaTd0001Driver::~TaTd0001Driver() {

}

void TaTd0001Driver::start() {
	//this->ref_config = this->configFactory->loadConfig(this->configFile);//
	LOG.o()->info("start");
	LOG.o()->info(this->configFileName);
	TaDriver::start();

	TaTickHandlerRef ref = this->container->getComponent(
			TaConstants::COM_ADVISOR_TICK_HANDLER);
	TaOrderAdvisorTickHandler* ath = ref.o();
	TaRef ref1 = this->container->getComponent(TaConstants::COM_ACCOUNT_INFOS);
	TaAccountInfos* ai = ref1.obj();
	TaConfigRef tradersCfg = this->ref_config.o()->getChild("traders");
	int size = tradersCfg.o()->getChildListSize("trader");

	for (int i = 0; i < size; i++) {
		TaConfigRef cfgRI = tradersCfg.o()->getChild("trader", i);
		TaMa2OrderAdvisor* oaI = new TaMa2OrderAdvisor();
		oaI->configure(cfgRI);
		oaI->setContainer(this->ref_container);//
		TaOrderAdvisorRef advI(oaI);
		bool succ = ath->tryAddStrategy(advI, true);
	}

	if (this->mql->getInfos()->isTester()) {

	}
	if (ai->isDemo()) {
		double res = ai->getBalance() - 300;
		LOG.o()->info(
				TaLang::__StringConcatenate(("is demo account,reserved:"),
						TaLang::__DoubleToString(res)));
		ai->setReserved(res); //
	}

}
