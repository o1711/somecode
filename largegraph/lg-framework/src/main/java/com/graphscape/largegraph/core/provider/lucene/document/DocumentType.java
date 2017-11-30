/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.util.BytesRef;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.util.ClassUtil;
import com.graphscape.largegraph.core.EdgeI;
import com.graphscape.largegraph.core.GraphI;
import com.graphscape.largegraph.core.VertexI;
import com.graphscape.largegraph.core.provider.lucene.Graph2VertexDocument;
import com.graphscape.largegraph.core.provider.lucene.LuceneEdge;
import com.graphscape.largegraph.core.provider.lucene.LuceneGraph;
import com.graphscape.largegraph.core.provider.lucene.LuceneVertex;
import com.graphscape.largegraph.core.provider.lucene.types.BaseFieldType;
import com.graphscape.largegraph.core.provider.lucene.types.StringFieldType;

public class DocumentType {

	public static final DocumentType VERTEX = new DocumentType("Vertex", VertexI.class, LuceneVertex.class);
	public static final DocumentType GRAPH = new DocumentType("Graph", GraphI.class, LuceneGraph.class);
	public static final DocumentType EDGE = new DocumentType("Edge", EdgeI.class, LuceneEdge.class);

	public static final DocumentType GRAPH2VERTEX = new DocumentType("G2V", Graph2VertexDocument.class,
			Graph2VertexDocument.class);

	private static Map<Class, DocumentType> ITF2TYPEMAP = new HashMap<Class, DocumentType>();

	static {
		ITF2TYPEMAP.put(VertexI.class, DocumentType.VERTEX);
		ITF2TYPEMAP.put(GraphI.class, DocumentType.GRAPH);
		ITF2TYPEMAP.put(EdgeI.class, DocumentType.EDGE);
		ITF2TYPEMAP.put(Graph2VertexDocument.class, DocumentType.GRAPH2VERTEX);

	}

	private static Map<Class, BaseFieldType> FIELDTYPE = new HashMap<Class, BaseFieldType>();

	private static Map<String, BaseFieldType> FIELDTYPE2 = new HashMap<String, BaseFieldType>();
	static {
		FIELDTYPE.put(String.class, new StringFieldType());
		FIELDTYPE2.put("s", new StringFieldType());

	}
	// protected MarshallerI marshaller;

	protected Class interface_;

	protected Class class_;

	protected String name;

	public static final String TYPE_PREFIX = "&";

	/**
	 * @param name
	 */
	public DocumentType(String name, Class itf, Class cls) {
		this.name = name;
		this.interface_ = itf;
		this.class_ = cls;
		// this.marshaller = new
		// MarshallerBuilder().jsonStringProvider().build();
	}

	public static DocumentType getType(Class itf) {
		return ITF2TYPEMAP.get(itf);
	}

	public Document buildDocument(String id, PropertiesI<Object> pts) {
		Document rt = new Document();
		this.addField(rt, "type", this.name);//
		this.addField(rt, "id", id);

		List<String> kL = pts.keyList();

		for (String key : kL) {
			Object value = pts.getProperty(key);
			this.addField(rt, key, value);
		}

		return rt;
	}

	public void removeField(Document doc, String key) {
		doc.removeField(key);
		doc.removeField(TYPE_PREFIX + key);// TODO
	}

	public List<String> getKeyList(Document doc) {
		List<String> rt = new ArrayList<String>();
		for (IndexableField iF : doc.getFields()) {
			String key = iF.name();
			if (key.startsWith(TYPE_PREFIX)) {
				continue;
			}
			rt.add(key);
		}

		return rt;

	}

	public Object getFieldValue(Document doc, String key) {
		IndexableField f = doc.getField(TYPE_PREFIX + key);
		if (f == null) {
			return null;
		}
		String typeCode = f.stringValue();
		BaseFieldType ft = FIELDTYPE2.get(typeCode);
		return ft.getFieldValue(doc, key);
	}

	public void addField(Document doc, String key, Object value) {
		if (value == null) {
			return;
		}
		Class cls = value.getClass();
		BaseFieldType ft = this.resolveFieldType(cls);
		ft.addField(doc, key, value);
	}

	public BaseFieldType resolveFieldType(Class cls) {
		BaseFieldType rt = FIELDTYPE.get(cls);

		if (rt == null) {
			throw new GsException("cannot resolve type for:" + cls + "todo");
		}
		return rt;
	}
	
	public <T> T newInstance(DocumentFactory df,Document doc){
		 return (T) ClassUtil.newInstance(this.class_, new Class[] { DocumentFactory.class, Document.class },
					new Object[] { df, doc });		 
	}

	/**
	 * @param value
	 * @return
	 */
	public BytesRef toBytesRef(Object value) {
		if (value == null) {
			return null;
		}
		Class cls = value.getClass();
		BaseFieldType ft = this.resolveFieldType(cls);
		return ft.toBytesRef(value);
	}

	public String getName() {
		return name;
	}
}