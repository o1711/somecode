/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file.provider;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.FileI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.support.ProxyBinaryHeader;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ProxyPlainFile extends ProxyBinaryHeader<FileI> implements FileI {

	/**
	 * @param t
	 */
	public ProxyPlainFile(FileI t) {
		super(t);
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
	public PlainRegionI getRegion(String index) {
		return this.target.getRegion(index);
	}

	@Override
	public PlainRegionI addRegion(String key,  long size) {
		return this.target.addRegion(key,  size);
	}

	@Override
	public PlainRegionI getTailRegion() {
		// TODO Auto-generated method stub
		return this.target.getTailRegion();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.FileI#getUnderlying()
	 */
	@Override
	public ByteCursorI getUnderlying() {
		// TODO Auto-generated method stub
		return this.target.getUnderlying();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.FileI#dump(java.io.Writer)
	 */
	@Override
	public void dumpInternal(Writer writer) throws IOException{
		this.target.dump(writer);
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.file.FileI#dump(java.io.PrintStream)
	 */
	@Override
	public void dump() {
		this.target.dump();
	}

	/* (non-Javadoc)
	 * @see com.graphscape.commons.file.FileI#delete()
	 */
	@Override
	public void delete() {
		this.target.delete();
	}

}
