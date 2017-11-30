/**
 * Jan 17, 2014
 */
package com.graphscape.largegraph.test.other;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.graphscape.commons.record.SerializerI;
import com.graphscape.commons.record.provider.serializer.IntSerializer;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class SerializerTest extends TestCase {

	public void testByteArray(){
		byte[] content = ByteArrayUtil.writeInt(510);
		int i2 = ByteArrayUtil.readInt(content);
		Assert.assertEquals(510, i2);
			
	}
	public void xtest() {
		
		
		SerializerI<Integer> ser = new IntSerializer();
		byte[] content = ser.serialize(510);
		int i2 = ser.deserialize(content);
		Assert.assertEquals(510, i2);
	}
}
