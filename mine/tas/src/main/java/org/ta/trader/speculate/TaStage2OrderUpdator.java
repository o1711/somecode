package org.ta.trader.speculate;

import org.ta.trader.TaConstants;
import org.ta.trader.executing.TaInternalOrder;
import org.ta.trader.executing.TaOrderUpdatingAction;
import org.ta.trader.hista.syriod.TaSyriodWindow;

public abstract class TaStage2OrderUpdator extends TaBaseOrderUpdator {

	public TaStage2OrderUpdator(TaSyriodWindow window) {
		super(window);
	}

	@Override
	public boolean tryUpdateOrder(TaOrderUpdatingAction uA) {
		TaInternalOrder io = uA.getInternalOrder();
		int stage = this.retriveStage(io);

		double lots = io.getOrderInfo().getLots();
		double originalRisk = io.getRisk();
		double currentPrice = this.getCurrentPrice(io.getDirection());
		if (stage == 1) { // close first risk of profit got

			// double
			double benifitRate = getProfitRateForStage1();
			if (io.isOrderProfitOfRisk(currentPrice, originalRisk, benifitRate)) {

				// move stop loss to the open price.
				// move take profit to 5*risk.
				double openPrice = io.getOrderInfo().getPrice();
				double stoploss2 = openPrice;
				double benefit2 = this.calculateFirstProfitForStage2(stoploss2);
				double takeprofit2 = stoploss2
						+ (io.isOrderUp() ? benefit2 : -benefit2);
				
				//
				uA.modifyOrder(openPrice, stoploss2,takeprofit2);

				// and close part of lots.
				double lotsToClose = this.calculateLotsToCloseForStage1(io); //
				this.closeOrder(uA, lotsToClose);
			}

		}

		if (stage == 2) { // last 1/3

			double astoploss2 = this.calculateStopLossForStage2();

			double astopLoss = io.getOrderStopLoss();

			double adelta = astoploss2 - astopLoss;
			if (io.getDirection().multiple(adelta) > 0.00005) {
				// doLog(STRINGConcatenate("stage2,modify for ma increasing."));

				double atakeprofit = io.getOrderTakeProfit();
				double atakeprofit2 = atakeprofit + adelta; //

				double aopenPrice = io.getOrderInfo().getPrice();
				// LOG.info(("modify order2"));
				uA.modifyOrder(aopenPrice, astoploss2,
						atakeprofit2);

			}

		}
		return false;
	}

	protected abstract double calculateStopLossForStage2();

	protected abstract double calculateFirstProfitForStage2(double stoploss2);

	protected double getProfitRateForStage1() {
		return TaConstants.OST_UPDATE_ORDER_BINIFIT_RATE_FOR_STAGE1;
	}

	protected int retriveStage(TaInternalOrder oi) {
		double lots = oi.getOrderInfo().getLots();
		double originalLots = oi.getLots(); //
		if (lots == originalLots) {
			return 1;
		} else {
			return 2;
		}
	}

	protected double calculateLotsToCloseForStage1(TaInternalOrder io) {
		return io.getLots() / 2;
	}

}
