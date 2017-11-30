package org.ta.trader.executing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ta.common.data.TaArrayData;
import org.ta.common.data.TaObjectData;

public class TaInternalOrderExecutorImpl implements TaInternalOrderExecutor {

	private Object orderOpenLock = new Object();

	private Object orderModifyLock = new Object();
	
	private Object orderCloseLock = new Object();

	private List<TaOrderOpenCommand> orderOpenList = new ArrayList<TaOrderOpenCommand>();

	private List<TaOrderModifyCommand> orderModifyList = new ArrayList<TaOrderModifyCommand>();

	private List<TaOrderCloseCommand> orderCloseList = new ArrayList<TaOrderCloseCommand>();

	protected Map<String, TaInternalOrder> orderMapByMagic = new HashMap<String, TaInternalOrder>();

	protected Map<String, TaInternalOrder> orderMapById = new HashMap<String, TaInternalOrder>();

	protected int nextInternalId;

	@Override
	public void open(TaInternalOrder io) {

		synchronized (this.orderOpenLock) {
			this.nextInternalId++;
			io.magic(this.nextInternalId + "");

			this.orderMapByMagic.put(io.getMagic(), io);

			// cache the command for flush.

			TaOrderOpenCommand ooc = new TaOrderOpenCommand()
					.magic(io.getMagic()).symbol(io.getSymbol())
					.lots(io.getLots()).otype(io.getOrderType())
					.price(io.getPrice()).sl(io.getSl()).tp(io.getTp());
			this.orderOpenList.add(ooc);
		}
	}

	@Override
	public void flush(TaObjectData odata) {
		this.flushOpen(odata);
		this.flushModify(odata);
		this.flushClose(odata);//
	}

	private void flushOpen(TaObjectData odata) {

		synchronized (this.orderOpenLock) {
			if(this.orderOpenList.isEmpty()){
				return;
			}

			TaArrayData cmdArray = new TaArrayData();
			
			odata.set("orderOpenCommandArray", cmdArray);
			for (int i = 0; i < orderOpenList.size(); i++) {
				TaOrderOpenCommand o = orderOpenList.get(i);
				cmdArray.add(TaOrderOpenCommand.toData(o));
			}
			this.orderOpenList.clear();
		}

	}

	private void flushModify(TaObjectData odata) {
	

		synchronized (this.orderModifyLock) {
			if(this.orderModifyList.isEmpty()){
				return;
			}
			TaArrayData cmdArray = new TaArrayData();

			odata.set("orderModifyCommandArray", cmdArray);
			for (int i = 0; i < this.orderModifyList.size(); i++) {
				TaOrderModifyCommand o = orderModifyList.get(i);
				cmdArray.add(TaOrderModifyCommand.toData(o));
			}
			this.orderModifyList.clear();
		}

	}

	private void flushClose(TaObjectData odata) {

		synchronized (this.orderCloseLock) {
			if(this.orderCloseList.isEmpty()){
				return;
			}

			TaArrayData cmdArray = new TaArrayData();
			
			odata.set("orderCloseCommandArray", cmdArray);
			for (int i = 0; i < this.orderCloseList.size(); i++) {
				TaOrderCloseCommand o = orderCloseList.get(i);
				cmdArray.add(TaOrderCloseCommand.toData(o));
			}
			this.orderCloseList.clear();
		}

	}

	@Override
	public boolean close(TaOrderCloseCommand occ) {
		synchronized (this.orderCloseLock) {
			this.orderCloseList.add(occ);
		}
		return false;
	}

	@Override
	public boolean modify(TaOrderModifyCommand omc) {
		synchronized (this.orderModifyLock) {
			this.orderModifyList.add(omc);
		}
		return false;
	}

	@Override
	public void update(List<TaOrderInfo> oiL) {

		for (int i = 0; i < oiL.size(); i++) {
			TaOrderInfo oi = oiL.get(i);
			String id = oi.getId();
			TaInternalOrder oiI = this.orderMapById.get(id);
			if (oiI == null) {// new ask from client.
				oiI = this.orderMapByMagic.get(oi.getMagic());

				this.orderMapById.put(id, oiI);
			}
			oiI.orderInfo(oi);//
		}

	}

	@Override
	public List<String> getOrderIdList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(this.orderMapById.keySet());
		return rt;
	}

	@Override
	public TaInternalOrder getOrderByMagic(String magic) {
		return this.orderMapByMagic.get(magic);
	}

	@Override
	public List<String> getOrderMagicList() {
		List<String> rt = new ArrayList<String>();
		rt.addAll(this.orderMapByMagic.keySet());
		return rt;
	}

	@Override
	public TaInternalOrder getOrderById(String id) {
		return this.orderMapById.get(id);
	}

}
