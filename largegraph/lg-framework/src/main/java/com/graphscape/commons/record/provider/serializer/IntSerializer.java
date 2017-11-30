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
public class IntSerializer implements SerializerI<Integer> {

	@Override
	public byte[] serialize(Integer t) {
		// TODO Auto-generated method stub
		return ByteArrayUtil.writeInt(t);
	}

	@Override
	public Integer deserialize(byte[] content) {
		// TODO Auto-generated method stub
		return ByteArrayUtil.readInt(0, content);

	}

}
