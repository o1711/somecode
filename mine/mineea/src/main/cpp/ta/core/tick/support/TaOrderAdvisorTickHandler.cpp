#include "TaOrderAdvisorTickHandler.h"

TaOrderAdvisorTickHandler::TaOrderAdvisorTickHandler(STRING pName) :
		TaTickHandler(pName) {
	this->initLogger("ta.core.tick.support.TaOrderAdvisorTickHandler"); //

	this->strategyList = new TaRefList();
	this->ref_strategyList = TaRef::ref(this->strategyList);

}

TaOrderAdvisorTickHandler::~TaOrderAdvisorTickHandler() {
	this->strategyList->clear(true);
}

TaOrderAdvisorRef TaOrderAdvisorTickHandler::getOrderAdvisor(int id) {
	for (int j = 0; j < strategyList->getSize(); j++) {
		TaOrderAdvisorRef os = strategyList->get(j);
		if (os.o()->getId() == id) {
			return os;
		}
	}
	return TaOrderAdvisorRef::Null();

}
bool TaOrderAdvisorTickHandler::tryAddStrategy(TaOrderAdvisorRef& os,
		bool destroyIfFailed) {
	STRING symbol = os.o()->getSymbol();
	int timeframe = os.o()->getTimeframe();
	//this->doLog("try add->");
	//TaLang::__Print("aaa");

	bool failed;
	if (TaLang::__Symbol() != symbol) {
		failed = true;
	}
	if (TaLang::__Period() != timeframe) {
		//	doLog("period:",IntegerTo(timeframe));
		failed = true;
	}

	if (failed) {
		this->logger.o()->warn(
				TaLang::__StringConcatenate("cannot add advisor,symbol:",
						symbol, "timeframe",
						TaLang::__IntegerToString(timeframe)));
	}else{
		this->logger.o()->info(TaLang::__StringConcatenate("success of add advisor,symbol:",
				symbol, "timeframe",
				TaLang::__IntegerToString(timeframe)));
	}

	if (!failed) {
		this->strategyList->addRef(os);
		this->logger.o()->info(
				TaLang::__StringConcatenate("success of adding advisor,symbol:", symbol,
						",timeframe:", TaLang::__IntegerToString(timeframe)));
	}

	//this->doLog("added->");
	return !failed;
}

void TaOrderAdvisorTickHandler::updateOrders() {
	int total = TaLang::__OrdersTotal();
	STRING symbol = TaLang::__Symbol();
	for (int i = 0; i < total; i++) {
		if (TaLang::__OrderSelect(i, TaLang::SELECT_BY_POS_,
				TaLang::MODE_TRADES_) == false) {
			continue;
		}
		if (TaLang::__OrderSymbol() != symbol) {
			continue;
		}

		int orderId = TaLang::__OrderTicket();

		TaOrderComments* oc = this->selectOriginalOrderComment(orderId);
		delete oc;
		if (!TaLang::__OrderSelect(orderId, TaLang::SELECT_BY_TICKET_)) {
			//what's happen?
			continue;
		}

		int magic = TaLang::__OrderMagicNumber();

		//this->doLog(STRINGConcatenate(//
		//		"orderId:", IntegerTo(orderId),//
		//		"magic:", IntegerTo(OrderMagicNumber())
		//		));
//		doLog(
//				STRINGConcatenate("orderId:", IntegerTo(orderId),
//						",comments:", TaLang::OrderComment(), ",originalComment:",
//						oc->to(),//
//						"originalRisk:",oc->getOriginalRisk(),//
//						""));
		TaOrderAdvisorRef oA = this->getOrderAdvisor(magic);
		if (oA.isNull()) { //?
			continue;
		}

		TaOrderUpdatingActionRef uA(new TaOrderUpdatingAction(orderId));
		oA.o()->tryUpdateOrder(uA);
	}
}

TaOrderComments* TaOrderAdvisorTickHandler::selectOriginalOrderComment(
		int orderId) {

	if (!TaLang::__OrderSelect(orderId, TaLang::SELECT_BY_TICKET_)) {
		return NULL;
	}
	STRING text = TaLang::__OrderComment();
	TaOrderComments* oc = new TaOrderComments(text);
//	doLog(STRINGConcatenate(
//			"orderId:",orderId,//
//			"text:",text,//
//			"isFrom:",oc->isFrom(),//
//			"from:",oc->getFrom(),//
//			"originalRisk,",TaLang::DoubleTo(oc->getOriginalRisk()),//
//
//	""));
	if (!oc->isFrom()) {

		return oc;
	}

	int orderIdFrom = oc->getFrom();
	delete oc;
	return selectOriginalOrderComment(orderIdFrom);

}

void TaOrderAdvisorTickHandler::onTickInternal() {
	this->updateOrders();

	for (int i = 0; i < strategyList->getSize(); i++) {
		TaRef ref =	this->strategyList->get(i);
		TaOrderAdvisor* os = ref.obj();
		TaOrderOpeningActionRef pA(new TaOrderOpeningAction());
		TaStringBufferRef sbr(new TaStringBuffer());
		//this->logger.o()->debug(sbr.o()->append("onTickInternal,advisor:")->append(os->getId()));
		bool opened = os->tryOpenOrder(pA);
	}
}
