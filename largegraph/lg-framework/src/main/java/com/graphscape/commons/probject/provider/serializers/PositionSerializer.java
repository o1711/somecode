/**
 * Jan 22, 2014
 */
package com.graphscape.commons.probject.provider.serializers;

import com.graphscape.commons.record.Position;
import com.graphscape.commons.record.SerializerI;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class PositionSerializer implements SerializerI<Position> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.SerializerI#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(Position t) {

		return ByteArrayUtil.writeLong(t.getPointer());//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.SerializerI#deserialize(byte[])
	 */
	@Override
	public Position deserialize(byte[] content) {
		long l = ByteArrayUtil.readLong(content);
		return new Position(l);

	}

}
