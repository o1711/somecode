/**
 * Jan 22, 2014
 */
package com.graphscape.commons.probject.provider.serializers;

import com.graphscape.commons.record.SerializerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ByteSerializer implements SerializerI<Byte> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.SerializerI#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(Byte t) {
		// TODO Auto-generated method stub
		return new byte[] { t.byteValue() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.SerializerI#deserialize(byte[])
	 */
	@Override
	public Byte deserialize(byte[] content) {
		// TODO Auto-generated method stub
		return Byte.valueOf(content[0]);//
	}

}
