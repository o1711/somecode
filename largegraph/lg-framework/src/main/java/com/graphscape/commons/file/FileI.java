/**
 * Jan 5, 2014
 */
package com.graphscape.commons.file;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface FileI extends DumpableI, HasRegionI {

	public ByteCursorI getUnderlying();

	public void delete();

}
