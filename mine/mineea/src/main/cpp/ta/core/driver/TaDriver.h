#ifndef TaDriver_MQH
#define TaDriver_MQH

#include "../../system/TaLang.h"
#include "../../mql/TaMql.h"
#include "../../mql/TaAccountInfos.h"
#include "../../common/TaConstants.h"
#include "../../common/config/TaConfig.h"
#include "../../common/config/TaConfigFactory.h"
#include "../container/TaContainer.h"
#include "../risk/TaTotalLossWeeklyRiskControl.h"
#include "../tick/TaTickHandler.h"
#include "../tick/support/TaCollectionTickHandler.h"
#include "../tick/support/TaOrderAdvisorTickHandler.h"
#include "../../common/log/TaLogger.h"
#include "../../core/order/TaOrderSender.h"

class TaDriver: public TaObject {
protected:
	static TaLoggerRef logger;
protected:
	STRING configFileName;
	STRING cfgFile;
	TaConfigFactory* configFactory;

	TaRef ref_configFactory;

	TaConfigRef ref_config;

	TaMql* mql;
	TaRef ref_mql;

	TaContainer* container;
	TaContainerRef ref_container;

	TaStringRefMap* indicatorMap;
	TaRef ref_indicatorMap;

public:
	TaDriver(STRING configFile);
	virtual ~TaDriver();
	virtual void start();
	virtual TaTickHandler* getStrategy();
	virtual TaAccountInfos* getAccountInfos();
	virtual TaContainer* getChart();
	virtual TaRef getMainTickHandler();
};

class TaDriverRef: public TaRef {
public:
	static TaDriverRef Null(){
		TaDriverRef ref;
		return ref;
	}
public:
	TaDriverRef(){}
	TaDriverRef(const TaRef& sr):TaRef(sr){}
	TaDriverRef(TaDriver* pPointer):TaRef(pPointer){}
	TaDriver* o() const {
		return this->pointer;
	}
};
#endif
