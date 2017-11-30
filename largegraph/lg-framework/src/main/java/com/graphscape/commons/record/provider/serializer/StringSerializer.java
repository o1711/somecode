/**
 * Jan 9, 2014
 */
package com.graphscape.commons.record.provider.serializer;

import com.graphscape.commons.record.SerializerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class StringSerializer implements SerializerI<String> {

	@Override
	public byte[] serialize(String t) {
		return t.getBytes();
	}

	@Override
	public String deserialize(byte[] content) {
		return new String(content);
	}

}
