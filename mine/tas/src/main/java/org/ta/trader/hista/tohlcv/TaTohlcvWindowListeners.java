package org.ta.trader.hista.tohlcv;

import java.util.ArrayList;
import java.util.List;

public class TaTohlcvWindowListeners implements TaTohlcvWindowListener {

	private List<TaTohlcvWindowListener> listenerList = new ArrayList<TaTohlcvWindowListener>();

	@Override
	public void onUpdate(TaTohlcvWindow window,int idx,TaTohlcv toh) {
		for (TaTohlcvWindowListener l : this.listenerList) {
			l.onUpdate(window,idx,toh);//
		}
	}

	public void add(TaTohlcvWindowListener l) {
		this.listenerList.add(l);
	}

}
