#ifndef TaOrderOpeningAction_MQH
#define TaOrderOpeningAction_MQH

#include "../../../common/lang/TaDouble.h"
#include "../../../common/TaAction.h"
#include "../../order/TaOrderComments.h"

class TaOrderOpeningAction: public TaAction {

protected:
	int orderCmd;
	double orderLots;
	double orderRisk;
	double orderPrice;
	double orderStoploss;
	double orderTakeprofit;
	int orderMagic;
	TaOrderOpeningAction* orderTaOrderOpeningAction;
	TaOrderComments* orderComments;

public:
	TaOrderOpeningAction();
	virtual ~TaOrderOpeningAction();

	void setOrderCmd(int pCmd);

	void setOrderTaOrderOpeningAction(TaOrderOpeningAction* pOrderTaOrderOpeningAction);

	void setOrderLots(double pOrderLots);
	void setOrderRisk(double pOrderRisk);
	void setOrderPrice(double pOrderPrice);
	void setOrderStoploss(double pOrderStoploss);
	void setOrderTakeprofit(double pOrderTakeprofit);
	void setOrderMagic(int magic);

	int getOrderCmd();
	double getOrderLots();
	double getOrderRisk();
	double getOrderPrice();
	double getOrderStoploss();
	double getOrderTakeprofit();
	int getOrderMagic();
	TaOrderOpeningAction* getOrderTaOrderOpeningAction();
	TaOrderComments* getOrderComments();

	virtual STRING to();

};

class TaOrderOpeningActionRef: public TaRef {
public:
	static TaOrderOpeningActionRef Null(){
		TaOrderOpeningActionRef ref;
		return ref;
	}
public:
	TaOrderOpeningActionRef(){}
	TaOrderOpeningActionRef(const TaRef& sr):TaRef(sr){}
	TaOrderOpeningActionRef(TaOrderOpeningAction* pPointer):TaRef(pPointer){}
	TaOrderOpeningAction* o() const {
		return this->pointer;
	}
};
#endif
