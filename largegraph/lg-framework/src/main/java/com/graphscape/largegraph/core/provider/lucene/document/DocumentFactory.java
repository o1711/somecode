/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DocumentFactory {

	protected Directory directory;

	protected DocumentWriter writer;

	protected DocumentReader reader;

	public DocumentFactory() {

		// directory = new RAMDirectory();
		try {
			this.directory = FSDirectory.open(new File("target\\lucene"));
		} catch (IOException e) {
			throw new GsException(e);
		}

		new DocumentWriter(this).close();// init directory.

		this.writer = new DocumentWriter(this);
		this.reader = new DocumentReader(this);

	}

	public DocumentReaderI reader() {
		if (this.writer != null) {
			this.writer.close();
			this.writer = null;
		}
		if (this.reader == null) {
			this.reader = new DocumentReader(this);
		}
		return this.reader;
	}

	public DocumentWriterI writer() {
		if (this.reader != null) {
			this.reader.close();
			this.reader = null;
		}
		if (this.writer == null) {
			this.writer = new DocumentWriter(this);
		}
		return this.writer;
	}

	public Directory getDirectory() {
		return directory;
	}

}
