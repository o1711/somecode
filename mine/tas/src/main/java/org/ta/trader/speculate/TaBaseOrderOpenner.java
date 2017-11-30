package org.ta.trader.speculate;


import org.ta.trader.TaConstants;
import org.ta.trader.TaSide;
import org.ta.trader.TaSymbol;
import org.ta.trader.executing.TaOrderOpeningAction;
import org.ta.trader.hista.syriod.TaSyriodWindow;
import org.ta.trader.manage.TaUserSession;
import org.ta.trader.rating.TaRateMonitor;

public abstract class TaBaseOrderOpenner implements TaOrderOpenner {

	protected TaSide direction;

	protected TaSyriodWindow window;
	
	protected double maxOrderRisk;
	
	protected double minOrderRisk;
	
	protected double takeProfitRate = 1.0;	
	
	protected String advisorId;
	
	protected TaRateMonitor quotesMonitor;
	
	protected TaSymbol symbol;
	
	public TaBaseOrderOpenner(String aid, TaSide dir, TaSyriodWindow window) {
		super();
		this.advisorId = aid;
		this.window = window;
		this.direction = dir;
		this.maxOrderRisk = TaConstants.OST_OPEN_ORDER_MAX_RISK;
		this.minOrderRisk = TaConstants.OST_OPEN_ORDER_MIN_RISK;
		this.symbol = window.getSyriod().getSymbol();
	}

	public void setTakeProfitRate(double pr) {
		this.takeProfitRate = pr;
	}

	protected double orderMarketPrice(TaSide dir) {
		if (dir.isUp()) {
			return this.quotesMonitor.getRate(this.symbol).getAsk();
		} else {
			return this.quotesMonitor.getRate(this.symbol).getBid();
		}
	}
	protected double orderStoploss(TaOrderOpeningAction oA) {
		double risk = oA.getInternalOrder().getRisk();
		double price = oA.getInternalOrder().getPrice();
		if (this.direction.isUp()) {
			risk = -risk;
		}
		return price + risk;
	}
	
	double orderTakeprofit(TaOrderOpeningAction oA) {
		double risk = oA.getInternalOrder().getRisk();
		double price = oA.getInternalOrder().getPrice();
		if (this.direction.isUp()) {
			return price + risk * this.takeProfitRate;
		} else {
			return price - risk * this.takeProfitRate;
		}
	}
	
	int exclusiveOrderType() {
		return -1;
	}
	
	protected boolean openOrder(TaOrderOpeningAction oA) {
		//this.doLog("sendOrder()");
		double risk =oA.getInternalOrder().getRisk();
		if (oA.getInternalOrder().getRisk() > this.maxOrderRisk) {
			//this.doLog(
			//		STRINGConcatenate("orderRisk:",
			//				TaLang::DoubleTo(oA.getInternalOrder().getRisk()),
			//				" is exceed the maxOrderRisk:",
			//				TaLang::DoubleTo(this.maxOrderRisk)));
			return false;
		}
		if (oA.getInternalOrder().getRisk() < this.minOrderRisk) {
			//this.doLog(
			//		STRINGConcatenate("orderRisk:",
			//				TaLang::DoubleTo(oA.getInternalOrder().getRisk()),
			//				" is lower the minOrderRisk:",
			//				TaLang::DoubleTo(this.minOrderRisk)));
			return false;
		}
		
		double stoploss = oA.getInternalOrder().getSl();

		if (stoploss == 0) {
			stoploss = this.orderStoploss(oA);
			oA.getInternalOrder().setSl(stoploss);
		}

		double takeprofit = oA.getInternalOrder().getTp();

		if (takeprofit == 0) {
			takeprofit = this.orderTakeprofit(oA);
			oA.getInternalOrder().setTp(takeprofit);
		}

		oA.getInternalOrder().setAdvisorId(this.advisorId);//
		oA.setOpenOrder(true);//
		return true;
	}

}
