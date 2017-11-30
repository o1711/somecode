/**
 * Jan 24, 2014
 */
package com.graphscape.commons.probject.provider;

import com.graphscape.commons.record.RecordType;
import com.graphscape.commons.record.SerializerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class PropertyType {
	protected RecordType recordType;
	protected SerializerI<?> serializer;
	protected Class clazz;

	public PropertyType(RecordType type, Class clazz, SerializerI<?> ser) {
		this.recordType = type;
		this.serializer = ser;
		this.clazz = clazz;
	}
}
