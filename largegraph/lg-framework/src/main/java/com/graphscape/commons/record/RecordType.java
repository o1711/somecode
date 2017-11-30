/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class RecordType {

	private static Map<Byte, RecordType> TYPES = new HashMap<Byte, RecordType>();

	private byte typeCode;

	private RecordType(byte typeCode) {
		this.typeCode = typeCode;
	}

	public byte getTypeCode() {
		return typeCode;
	}

	public static RecordType valueOf(byte type) {
		RecordType rt = TYPES.get(type);
		if (rt == null) {
			synchronized (TYPES) {
				rt = TYPES.get(type);
				if (rt == null) {
					rt = new RecordType(type);
					TYPES.put(type, rt);
				}
			}
		}

		return rt;

	}

	@Override
	public int hashCode() {
		return typeCode;
	}

	@Override
	public String toString() {
		return typeCode + "";
	}

}
