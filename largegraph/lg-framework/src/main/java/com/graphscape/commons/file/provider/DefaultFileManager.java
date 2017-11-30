/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file.provider;

import org.apache.commons.vfs2.AllFileSelector;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultFileManager extends AbstractVfsFileManager {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultFileManager.class);

	FileSystemManager fileSystemManager;

	private String rootDir;

	// private static String ROOT = "ram:///";
	// private static String ROOT = "file:///d:/tmp/";

	public DefaultFileManager(String url) {
		this.rootDir = url;
	}
	
	@Override
	public void openInternal() {
		try {
			this.fileSystemManager = VFS.getManager();

			String dir = rootDir;
			if (LOG.isDebugEnabled()) {
				LOG.debug("openning file manager:" + dir);
			}

			this.baseFolder = this.fileSystemManager.resolveFile(dir);

			if (!this.baseFolder.exists()) {
				this.baseFolder.createFolder();
			}
		} catch (FileSystemException e) {
			throw new GsException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.FileManagerI#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.FileManagerI#delete()
	 */
	@Override
	public void delete() {
		try {
			int num = this.baseFolder.delete(new AllFileSelector());
			if (LOG.isWarnEnabled()) {
				LOG.warn("all " + num + " files deleted from folder:" + this.baseFolder.getURL());
			}
		} catch (FileSystemException e) {
			throw new GsException(e);
		}
	}

}
