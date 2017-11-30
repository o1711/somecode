package org.ta.trader.hista.syriod;

import java.util.HashMap;
import java.util.Map;

import org.ta.trader.TaSymbol;
import org.ta.trader.hista.TaPeriod;

public class TaSyriod {

	private static Map<TaSymbol, Map<TaPeriod, TaSyriod>> MAP = new HashMap<TaSymbol, Map<TaPeriod, TaSyriod>>();

	private TaSymbol symbol;

	private TaPeriod period;

	private String stringValue;

	private TaSyriod(TaSymbol s, TaPeriod p) {
		this.symbol = s;
		this.period = p;
		this.stringValue = s.getStringValue() + "." + p.name();
	}

	public String getStringValue() {
		return stringValue;
	}

	@Override
	public int hashCode() {
		return this.stringValue.hashCode();
	}

	@Override
	public String toString() {
		return this.stringValue;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!TaSyriod.class.isInstance(obj)) {
			return false;
		}
		TaSyriod s = (TaSyriod) obj;

		return s.stringValue.equals(this.stringValue);
	}

	public static TaSyriod valueOf(String syriod) {
		String[] ss = syriod.split("\\.");
		return valueOf(ss[0], ss[1]);
	}
	
	public static TaSyriod valueOf(String sym, String period) {
		TaSymbol s = TaSymbol.valueOf(sym);
		TaPeriod p = TaPeriod.valueOf(period);
		return valueOf(s,p);
	}
	
	public static TaSyriod valueOf(TaSymbol s, TaPeriod p) {
		Map<TaPeriod, TaSyriod> map2 = MAP.get(s);
		if (map2 == null) {
			map2 = new HashMap<TaPeriod, TaSyriod>();
			MAP.put(s, map2);
		}
		TaSyriod rt = map2.get(p);
		if (rt == null) {
			rt = new TaSyriod(s, p);
			map2.put(p, rt);
		}
		return rt;
	}

	public TaSymbol getSymbol() {
		return symbol;
	}

	public TaPeriod getPeriod() {
		return period;
	}
}
