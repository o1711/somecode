/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.test.other;

import junit.framework.TestCase;

import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.lucene.LuceneLargeGraph;
import com.graphscape.largegraph.core.provider.lucene.document.DocumentFactory;
import com.graphscape.largegraph.core.provider.lucene.document.operations.AddDocumentOperation;
import com.graphscape.largegraph.core.provider.lucene.document.operations.GetElementOperation;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DocumentFactoryTest extends TestCase {

	public void test() {
		DocumentFactory df = new DocumentFactory();
		VertexI v = new AddDocumentOperation<VertexI>(df, VertexI.class, "0").execute();
		v.setProperty("property1", "p1value");

		v = new GetElementOperation<VertexI>(df, VertexI.class, "0", true).execute();

		String value = (String) v.getProperty("property1");
		assertEquals("p1value", value);

		LuceneLargeGraph lg = new LuceneLargeGraph();
		GraphI g = lg.getRootGraph();
		assertNotNull(g);
	}
}
