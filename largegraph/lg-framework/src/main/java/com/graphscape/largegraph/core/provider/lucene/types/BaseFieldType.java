/**
 * Dec 30, 2013
 */
package com.graphscape.largegraph.core.provider.lucene.types;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.util.BytesRef;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class BaseFieldType<T> {

	protected FieldType fieldType = new FieldType();

	protected String typeCode;

	public BaseFieldType(String typeCode) {
		this.typeCode = typeCode;
		this.fieldType.setIndexed(true);
		this.fieldType.setTokenized(false);
		this.fieldType.setStored(true);
	}

	public Field addField(Document doc, String key, T value) {
		Field tField = new Field("&" + key, this.typeCode, this.fieldType);
		doc.add(tField);
		return this.doAddField(doc, key, value);
	}

	public abstract Field doAddField(Document doc, String key, T value);

	/**
	 * @param doc
	 * @param key
	 */
	public abstract T getFieldValue(Document doc, String key);

	/**
	 * @param value
	 * @return
	 */
	public abstract BytesRef toBytesRef(T value);

}
