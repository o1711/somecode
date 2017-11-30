/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.types;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.util.BytesRef;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class StringFieldType extends BaseFieldType<String> {

	public StringFieldType() {
		super("s");
	}

	@Override
	public Field doAddField(Document doc, String key, String value) {
		Field rt = new Field(key, value, this.fieldType);
		doc.add(rt);
		return rt;
	}

	@Override
	public String getFieldValue(Document doc, String key) {
		IndexableField f = doc.getField(key);

		return f.stringValue();
	}

	@Override
	public BytesRef toBytesRef(String value) {
		
		return new BytesRef(value);
	}

}
