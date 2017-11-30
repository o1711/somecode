/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface DocumentReaderI {

	public List<Document> getDocumentList(Query bq);

}