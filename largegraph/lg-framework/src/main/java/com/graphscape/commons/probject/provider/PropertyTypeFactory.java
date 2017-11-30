/**
 * Jan 24, 2014
 */
package com.graphscape.commons.probject.provider;

import java.util.HashMap;
import java.util.Map;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.RecordType;
import com.graphscape.commons.record.provider.serializer.StringSerializer;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class PropertyTypeFactory {

	protected Map<RecordType, PropertyType> types;

	protected Map<Class, PropertyType> classTypeMap;

	public PropertyTypeFactory() {
		this.types = new HashMap<RecordType, PropertyType>();
		this.classTypeMap = new HashMap<Class, PropertyType>();
		this.add(new PropertyType(RecordType.valueOf((byte) 0), String.class, new StringSerializer()));
	}

	public void add(PropertyType ptype) {
		if (null != this.getPropertyType(ptype.recordType)) {
			throw new GsException("duplicated");
		}
		this.types.put(ptype.recordType, ptype);
		this.classTypeMap.put(ptype.clazz, ptype);
	}

	public PropertyType getPropertyType(RecordType type) {
		return types.get(type);
	}

	public PropertyType getPropertyType(RecordType type, boolean force) {
		PropertyType rt = this.getPropertyType(type);
		if (rt == null && force) {
			throw new GsException("no property type:" + type);
		}
		return rt;
	}

	public PropertyType getPropertyType(Class cls, boolean force) {
		PropertyType rt = this.getPropertyType(cls);
		if (rt == null && force) {
			throw new GsException("no property type:" + cls);
		}
		return rt;
	}

	public PropertyType getPropertyType(Class cls) {
		return this.classTypeMap.get(cls);
	}

	public void add(RecordType type, Class<String> class1, StringSerializer ser) {
		this.add(new PropertyType(type, class1, ser));
	}

}
