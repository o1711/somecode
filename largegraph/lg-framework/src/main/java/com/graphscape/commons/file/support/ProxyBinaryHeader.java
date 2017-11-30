package com.graphscape.commons.file.support;

import com.graphscape.commons.file.HasRegionI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.RegionI;

public abstract class ProxyBinaryHeader<T extends HasRegionI> extends DumpableSupport implements HasRegionI {

	protected T target;
	public ProxyBinaryHeader(T t) {
		this.target = t;
	}

	@Override
	public PlainRegionI getRegion(String name) {
		return this.target.getRegion(name);
	}

	@Override
	public PlainRegionI addRegion(String key, long size) {
		return this.target.addRegion(key, size);
	}

	@Override
	public void open() {
		this.target.open();
	}

	@Override
	public void close() {
		this.target.close();
	}

	@Override
	public void clear() {
		this.target.clear();
	}

	@Override
	public PlainRegionI getTailRegion() {
		// TODO Auto-generated method stub
		return this.target.getTailRegion();//
	}

}
