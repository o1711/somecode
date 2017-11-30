package com.graphscape.commons.file.support;

import com.graphscape.commons.file.RegionI;
import com.graphscape.commons.lang.Wrapper;

public class ProxyRegion<T extends RegionI> extends Wrapper<T> implements RegionI {

	public ProxyRegion(T t) {
		super(t);
	}

	@Override
	public long getLength() {
		return this.target.getLength();
	}

	@Override
	public long getCapacity() {
		
		return this.target.getCapacity();
	}

}
