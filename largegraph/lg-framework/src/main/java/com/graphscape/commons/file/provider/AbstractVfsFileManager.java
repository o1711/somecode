/**
 * Jan 4, 2014
 */
package com.graphscape.commons.file.provider;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.FileI;
import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class AbstractVfsFileManager implements FileManagerI {

	DefaultFileSystemManager fileSystemManager;

	protected FileObject baseFolder;

	protected boolean open;

	public AbstractVfsFileManager() {

	}

	@Override
	public FileManagerI open() {
		this.openInternal();
		this.open = true;
		return this;
	}

	protected abstract void openInternal();

	private void assertOpen() {
		if (!open) {
			throw new GsException("please call open() first.");
		}
	}

	@Override
	public FileI getOrCreateFile(String name) {
		return this.getOrCreateFile(name, 1024);//
	}

	@Override
	public FileI getOrCreateFile(String name, int headerSpace) {
		try {
			this.assertOpen();
			FileObject fo = this.baseFolder.resolveFile(name);
			if (!fo.exists()) {
				fo.createFile();
			}

			return new DefaultFile(fo, headerSpace);// max header
		} catch (FileSystemException e) {
			throw new GsException(e);
		}

	}

}
