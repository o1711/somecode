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
public class ShortSerializer implements SerializerI<Short> {

	@Override
	public byte[] serialize(Short t) {
		// TODO Auto-generated method stub
		return ByteArrayUtil.writeInt(t);
	}

	@Override
	public Short deserialize(byte[] content) {
		// TODO Auto-generated method stub
		return ByteArrayUtil.readShort(0, content);

	}

}
