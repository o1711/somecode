package org.ta.common.data;

import java.util.ArrayList;
import java.util.List;

public class TaArrayData extends TaData {

	private List<TaData> elementList;

	public TaArrayData() {
		this.elementList = new ArrayList<TaData>();
	}

	public void add(int idx, TaData dataI) {
		if(idx == -1){			
			this.elementList.add(dataI);
		}else{
			this.elementList.add(idx,dataI);
		}
	}

	public void add(TaData dataI) {
		this.elementList.add(dataI);
	}

	public TaData get(int idx) {
		return this.elementList.get(idx);
	}

	public int size() {
		return this.elementList.size();
	}

	@Override
	public String toString() {
		return TaArrayData.class.getSimpleName() + this.elementList.toString();
	}

}
