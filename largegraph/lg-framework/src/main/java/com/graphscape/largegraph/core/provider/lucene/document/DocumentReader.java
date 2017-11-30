/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DocumentReader implements DocumentReaderI {
	// reader
	protected DirectoryReader ireader;
	protected IndexSearcher isearcher;
	protected Directory directory;
	protected DocumentFactory fac;

	/**
	 * @param df
	 * @param directory
	 */
	public DocumentReader(DocumentFactory df) {
		this.fac = df;
		this.directory = df.getDirectory();
		try {
			ireader = DirectoryReader.open(directory);
			isearcher = new IndexSearcher(ireader);
		} catch (IOException e1) {
			throw GsException.toRuntimeException(e1);
		}
	}

	public List<Document> getDocumentList(Query bq) {

		List<Document> rt = new ArrayList<Document>();
		try {
			ScoreDoc[] hits = isearcher.search(bq, null, 1000).scoreDocs;
			for (int i = 0; i < hits.length; i++) {
				rt.add(isearcher.doc(hits[i].doc));
			}
		} catch (IOException e) {
			throw new GsException(e);
		}
		return rt;
	}

	/**
	 * 
	 */
	public void close() {
		try {
			this.ireader.close();
			this.ireader = null;
			this.isearcher = null;
		} catch (IOException e) {
			throw new GsException(e);
		}
	}

}
