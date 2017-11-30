package com.graphscape.commons.file;

import com.graphscape.commons.lang.ResourceI;

public interface HasRegionI extends ResourceI {

	public PlainRegionI getRegion(String key);//

	public PlainRegionI addRegion(String key, long size);

	public void clear();

	public PlainRegionI getTailRegion();

}
