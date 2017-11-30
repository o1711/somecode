/**
 * Jan 9, 2014
 */
package com.graphscape.commons.record.provider.serializer;

import com.graphscape.commons.record.SerializerI;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class LongSerializer implements SerializerI<Long> {

	@Override
	public byte[] serialize(Long t) {
		// TODO Auto-generated method stub
		return ByteArrayUtil.writeLong(t);
	}

	@Override
	public Long deserialize(byte[] content) {
		// TODO Auto-generated method stub
		return ByteArrayUtil.readLong(0, content);

	}

}
