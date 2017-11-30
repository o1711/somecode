package com.graphscape.commons.file;


public interface PlainRegionI extends RegionI,DumpableI, ByteReadableI, ByteWritableI {

	public PlainRegionI subRegion(long offset, long len);
	
	public PlainRegionI subRegion(long offset);
	

}
