package org.ta.trader.executing;


public class TaOrderUpdatingAction {

	private TaInternalOrder order;

	private TaOrderModifyCommand orderModifyCommand;

	private TaOrderCloseCommand orderCloseCommand;

	public TaOrderCloseCommand getOrderCloseCommand() {
		return orderCloseCommand;
	}

	public TaOrderUpdatingAction(TaInternalOrder oi) {
		this.order = oi;
	}

	public TaInternalOrder getInternalOrder() {
		return order;
	}

	public TaOrderModifyCommand getOrderModifyCommand() {
		return orderModifyCommand;
	}

	public void closeOrder(double price, double lots) {
		this.orderCloseCommand = new TaOrderCloseCommand()
				.orderId(this.order.getOrderInfo().getId()).price(price)
				.lots(lots);
	}

	public void modifyOrder(double price, double sl, double tp) {
		this.orderModifyCommand = new TaOrderModifyCommand()
				.orderId(this.order.getOrderInfo().getId()).price(price).sl(sl)
				.tp(tp);

	}

}
