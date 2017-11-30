/**
 * Jan 22, 2014
 */
package com.graphscape.commons.probject.provider.serializers;

import java.io.UnsupportedEncodingException;

import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.SerializerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class StringSerializer implements SerializerI<String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.record.SerializerI#serialize(java.lang.Object)
	 */
	@Override
	public byte[] serialize(String t) {

		try {
			return t.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new GsException(e);
		}//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.SerializerI#deserialize(byte[])
	 */
	@Override
	public String deserialize(byte[] content) {
		// TODO Auto-generated method stub
		try {
			return new String(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new GsException(e);
		}
	}

}
