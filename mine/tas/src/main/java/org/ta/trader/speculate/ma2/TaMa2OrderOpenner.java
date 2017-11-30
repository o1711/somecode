package org.ta.trader.speculate.ma2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ta.common.util.TaTimeUtil;
import org.ta.trader.TaSide;
import org.ta.trader.executing.TaInternalOrder;
import org.ta.trader.executing.TaOrderOpeningAction;
import org.ta.trader.executing.TaOrderType;
import org.ta.trader.hista.indicator.TaMovingAverageConvergenceDivergenceIndicator;
import org.ta.trader.hista.syriod.TaSyriodWindow;
import org.ta.trader.hista.tohlcv.TaTwoMovingAverageTimeBar;
import org.ta.trader.speculate.TaBaseOrderOpenner;

public class TaMa2OrderOpenner extends TaBaseOrderOpenner {

	private static Logger LOG = LoggerFactory.getLogger(TaMa2OrderOpenner.class);

	protected TaTwoMovingAverageTimeBar twoMa;

	protected int maxBarsForCrossing;

	protected int minBarsBeforeCrossing;

	protected int maxBarsForWaitingMacd;
	
	protected TaMovingAverageConvergenceDivergenceIndicator macd;

	protected double orderLots_;

	public TaMa2OrderOpenner(String id, String string, TaSide direction, TaSyriodWindow window) {
		super(id, direction, window);
		this.maxBarsForWaitingMacd = 1;
		this.maxBarsForCrossing = 3;// 5 is not good for EURUSD
		this.minBarsBeforeCrossing = 5;
		this.twoMa = new TaTwoMovingAverageTimeBar(20, 100, window);
		this.macd = new TaMovingAverageConvergenceDivergenceIndicator(12, 26, window);
	}

	public void setOrderLots(double orderLots_) {
		this.orderLots_ = orderLots_;
	}

	@Override
	public boolean tryOpenOrder(TaOrderOpeningAction pA) {

		long time0 = window.get().getTime();
		if (time0 == TaTimeUtil.parse("2015.04.13 09.10.00")) {
			LOG.info("time0:" + time0);//			
		}
		// this.logger.o().debug("tryOpen,0");
		int startIndex = this.getStartIndex();
		if (startIndex == -1) {
			return false; // no crossing happen.
		}
		// this.logger.o().debug("tryOpen,1");
		int crossedIndex = this.getCrossedIndex(startIndex);

		if (crossedIndex == -1) { // no crossing happen
			return false;
		}

		if (crossedIndex > 1 + this.maxBarsForWaitingMacd) {// crossed happen
															// long ago.
			return false;
		}

		// this.logger.o().debug("tryOpen,2");

		int macdFirstMatched = this.getMacdFirstMachedIndex(crossedIndex);

		if (macdFirstMatched == -1) { // no MACD matched.
			return false;
		}

		// this.logger.o().debug("tryOpen,3");
		// if crossedINdex ==1
		// macdFirstMatched is 1 or 0,in both case, it's time to open order.
		// other wise,it must be the current bar to open order.

		if (crossedIndex > 1 && macdFirstMatched != 0) {
			// already created before.
			return false;
		}
		// this.logger.o().debug("tryOpen,4");
		if (!this.isAllBeforeCrossingOpenCloseMatch(startIndex)) {
			return false;
		}
		// this.logger.o().debug("tryOpen,5");
		return this.openOrder(pA, startIndex, crossedIndex);
	}

	protected boolean openOrder(TaOrderOpeningAction pA, int startCrossingIndex, int crossedIndex) {
		// //this.doLog("true,1");
		//
		double price = this.orderMarketPrice(this.direction);
		double risk = this.orderRisk(price, crossedIndex);
		double lots = this.orderLots();
		TaInternalOrder io = pA.getInternalOrder();
		io.setOrderType(TaOrderType.MARKET);
		io.setPrice(price);
		io.setLots(lots); //
		io.setRisk(risk); // //this.doLog("true,2"); this.sendOrder(pA);
		return this.openOrder(pA);//
	}

	protected double orderLots() {
		return this.orderLots_;
	}

	protected double orderRisk(double price, int crossedIndex) {
		double rt = 0.0;
		if (this.direction.isUp()) {
			rt = price - this.twoMa.getLow(crossedIndex) + 0.00010;
		} else {
			rt = -price + this.twoMa.getHigh(crossedIndex) + 0.00010;
		}
		return rt;
	}

	private int getStartIndex() {
		//
		int rt = -1;
		for (int i = 1; i < 1 + this.maxBarsForCrossing; i++) {

			if (this.twoMa.isOpenInSideOfAllMa((this.direction.getNegative()), i)) {
				rt = i;
				break;
			}

		}

		return rt;
	}

	private int getCrossedIndex(int startIndex) {
		int rt = -1;
		for (int i = startIndex; i >= 1; i--) {

			if (this.twoMa.isCloseInSideOfAllMa(this.direction, i)) {
				rt = i;
				break;
			}
		}

		return rt;
	}

	private int getMacdFirstMachedIndex(int crossedIndex) {
		int rt = -1;
		for (int i = crossedIndex; i >= 0; i--) {
			if (this.isMacdMatched(i)) {
				rt = i;
				break;
			}
		}
		return rt;
	}

	boolean isAllBeforeCrossingOpenCloseMatch(int startCrossingIndex) {

		if (!twoMa.isAllOpenCloseInSideOfSlowMa((this.direction.getNegative()), startCrossingIndex + 1,
				startCrossingIndex + this.minBarsBeforeCrossing)) {
			// this.doLog("false,27");
			return false;
		}

		return true;
	}

	private boolean isMacdMatched(int idx) {

		double macd0 = this.macd.getValue(idx);
		// TODOTaLang::__iMACD(NULL, 0, 12, 26, 9,
		// TaLang::PRICE_CLOSE_, TaLang::MODE_MAIN_, idx);
		return this.direction.isSignEquals(macd0);
	}
}
