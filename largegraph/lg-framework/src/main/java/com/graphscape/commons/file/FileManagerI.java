/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface FileManagerI {

	public FileManagerI open();

	public FileI getOrCreateFile(String name);

	public FileI getOrCreateFile(String name, int headerSpace);

	public void close();
	
	public void delete();

}
