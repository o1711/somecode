#include "TaDriver.h"
#include "../../common/config/TaJsonConfigFactory.h"

TaLoggerRef TaDriver::logger = TaLoggerFactory::getLogger("TaDriver");

TaDriver::TaDriver(STRING pConfigFile){

	this->configFileName = pConfigFile;
	if(this->configFileName == NULL){
		logger.o()->info("configFile==NULL");
	}
	this->cfgFile = this->configFileName;
	this->container = new TaContainer();
	this->ref_container = TaRef::ref(this->container);

	this->mql = new TaMql();
	this->ref_mql = TaRef::ref(this->mql);

	this->indicatorMap = new TaStringRefMap();
	this->ref_indicatorMap = TaRef::ref(this->indicatorMap);

	this->configFactory = new TaJsonConfigFactory(this->configFileName);
	this->ref_configFactory = TaRef::ref(this->configFactory);
}

TaDriver::~TaDriver(){
	//delete this->indicatorMap;
	//this->chart->destroy();
	//delete this->chart;
	//delete this->mql;
	//delete this->configFactory;
}

void TaDriver::start(){
	if(this->configFileName == NULL){
		logger.o()->info("configFile is NULL");
	}
	logger.o()->info("start");
	logger.o()->info(this->configFileName);

	this->ref_config = this->configFactory->loadConfig();
	//this->addIndicator(TaConstants::IDR_SUPER_TIME,new TaSuperTimeIndicator(this->chart));
	TaRef ref1(new TaAccountInfos());

	this->container->addComponent(TaConstants::COM_ACCOUNT_INFOS,ref1);


	TaTotalLossWeeklyRiskControl* obj2 = new TaTotalLossWeeklyRiskControl();
	obj2->setContainer(this->ref_container);
	TaRef ref2(obj2);
	this->container->addComponent(TaConstants::COM_TOTAL_LOSS_WEEKLY_RISK_CONTROL,ref2);

	TaOrderSender* obj3 = new TaOrderSender();
	obj3->setContainer(this->ref_container);
	TaRef ref3(obj3);
	this->container->addComponent(("componentOrderSender"),ref3);

	TaRef ref4(new TaCollectionTickHandler(TaConstants::COM_TICK_HANDLER));
	this->container->addComponent(TaConstants::COM_TICK_HANDLER, ref4);

	TaRef ref5(new TaOrderAdvisorTickHandler(("order-advisor-tick-handler")));
	this->container->addComponent(TaConstants::COM_ADVISOR_TICK_HANDLER,ref5);

	TaRef ref6= this->container->getComponent(TaConstants::COM_TOTAL_LOSS_WEEKLY_RISK_CONTROL);
	TaTotalLossWeeklyRiskControl * rc = ref6.obj();
	rc->setMaxWeekLossRate(0.05);

	TaRef ref7 = this->getMainTickHandler();
	TaCollectionTickHandler* os = ref7.obj();
	//os->add(new TaTwoMovingAverageOrderOpeningStrategy("ma2", TaConstants::DIR_UP, this->chart));
	TaTickHandlerRef ref8=this->container->getComponent(TaConstants::COM_ADVISOR_TICK_HANDLER);
	os->add(ref8);

}
TaRef TaDriver::getMainTickHandler(){
	return this->container->getComponent(TaConstants::COM_TICK_HANDLER);
}


TaContainer* TaDriver::getChart(){
	return this->container;
}
