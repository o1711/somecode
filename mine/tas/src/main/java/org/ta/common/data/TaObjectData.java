package org.ta.common.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ta.common.config.TaException;
import org.ta.common.util.TaTimeUtil;
import org.ta.trader.TaSymbol;
import org.ta.trader.executing.TaOrderType;
import org.ta.trader.hista.TaPeriod;
import org.ta.trader.hista.syriod.TaSyriod;

public class TaObjectData extends TaData {

	private Map<String, TaData> propertyMap;

	public TaObjectData() {
		propertyMap = new HashMap<String, TaData>();
	}

	public TaObjectData propertyTime(String key, long value) {
		String timeS = TaTimeUtil.format(value);
		return this.property(key, timeS);//
	}

	public TaObjectData property(String key, long value) {
		return property(key, String.valueOf(value));//
	}

	public TaObjectData property(String key, TaData value) {
		set(key, value);//
		return this;
	}

	public TaObjectData property(String key, double value) {
		return property(key, String.valueOf(value));//
	}

	public TaObjectData property(String key, String value) {
		set(key, value);
		return this;
	}

	public void set(String key, String value) {
		this.set(key, new TaStringData(value));
	}

	public void set(String keyI, TaData dataI) {
		this.propertyMap.put(keyI, dataI);
	}

	public TaSymbol getPropertyAsSymbol(String key) {
		return this.getPropertyAsSymbol(key, false);
	}

	public TaSymbol getPropertyAsSymbol(String key, boolean force) {
		String value = this.getPropertyAsString(key, force);
		return TaSymbol.valueOf(value);
	}

	public TaOrderType getPropertyAsOrderType(String key) {
		return this.getPropertyAsOrderType(key, false);
	}

	public TaOrderType getPropertyAsOrderType(String key, boolean force) {
		String value = this.getPropertyAsString(key, force);
		return TaOrderType.valueOf(value);
	}

	public double getPropertyAsDouble(String key) {
		String value = this.getPropertyAsString(key, true);
		return Double.parseDouble(value);
	}

	public boolean getPropertyAsBoolean(String key) {
		String value = this.getPropertyAsString(key);
		return "true".equals(value) || "Y".equals(value);//
	}

	public int getPropertyAsInt(String key) {
		String value = this.getPropertyAsString(key);
		return Integer.parseInt(value);//
	}

	public TaSyriod getPropertyAsSyriod(String key) {
		String value = this.getPropertyAsString(key);
		return TaSyriod.valueOf(value);
	}

	public TaPeriod getPropertyAsPeriod(String key) {
		String value = this.getPropertyAsString(key);
		return TaPeriod.valueOf(value);
	}

	public String getPropertyAsString(String key) {
		return this.getPropertyAsString(key, false);
	}

	public String getPropertyAsString(String key, boolean force) {
		TaStringData sdata = (TaStringData) this.get(key);
		if (sdata == null) {
			if (force) {
				throw new TaException("no property found:" + key + ",all:"
						+ this.propertyMap.keySet());
			}
			return null;
		}
		return sdata.getStringValue();
	}

	public TaData get(String keyI) {
		return this.propertyMap.get(keyI);
	}

	public Set<String> keySet() {
		//
		return this.propertyMap.keySet();
	}

	public long getPropertyAsTime(String key) {
		String value = this.getPropertyAsString(key);
		return TaTimeUtil.parse(value);
	}
	public boolean isEmpty(){
		return this.propertyMap.isEmpty();
	}
	@Override
	public String toString() {
		return TaObjectData.class.getSimpleName() + this.propertyMap.toString();
	}

}
