/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DocumentWriter implements DocumentWriterI {
	// Directory directory = FSDirectory.open("/tmp/testindex");

	protected Directory directory;

	protected IndexWriterConfig writerConfig;
	protected IndexWriter iwriter;
	protected Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);

	public DocumentWriter(DocumentFactory df) {
		this.directory = df.getDirectory();
		try {// init
			writerConfig = new IndexWriterConfig(Version.LUCENE_46, analyzer);
			iwriter = new IndexWriter(directory, writerConfig);
		} catch (IOException e) {
			throw new GsException(e);
		}
		//
	}

	public void deleteDocument(Query q) {
		try {
			iwriter.deleteDocuments(q);
		} catch (IOException e) {
			throw new GsException(e);
		}
	}

	public void addDocument(Document doc) {

		try {

			iwriter.addDocument(doc);

		} catch (IOException e) {
			throw new GsException(e);
		}
	}

	public void commit() {
		try {
			this.iwriter.commit();
		} catch (IOException e) {
			throw new GsException(e);
		}
	}

	/**
	 * 
	 */
	public void close() {
		try {
			this.iwriter.close();
		} catch (IOException e) {
			throw new GsException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.largegraph.core.provider.lucene.document.DocumentWriterI
	 * #deleteDocuments(org.apache.lucene.search.Query)
	 */
	@Override
	public void deleteDocuments(Query query) {
		try {
			this.iwriter.deleteDocuments(query);
		} catch (IOException e) {
			throw new GsException(e);
		}
	}

}
