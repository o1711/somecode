/**
 * Dec 31, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface DocumentWriterI {

	public void deleteDocument(Query q);

	public void deleteDocuments(Query query);

	public void addDocument(Document doc);

	public void commit();

}
