/**
 * Jan 24, 2014
 */
package com.graphscape.commons.probject.provider;

import com.graphscape.commons.record.SerializerI;
import com.graphscape.commons.record.provider.serializer.StringSerializer;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class PropertyKeySerializer implements SerializerI<PropertyKey> {

	private StringSerializer stringSerializer = new StringSerializer();

	@Override
	public byte[] serialize(PropertyKey t) {
		byte[] id = this.stringSerializer.serialize(t.getId());
		byte[] key = this.stringSerializer.serialize(t.getKey());
		byte[] idLen = ByteArrayUtil.writeInt(id.length);

		return ByteArrayUtil.concate(idLen, id, key);//

	}

	@Override
	public PropertyKey deserialize(byte[] content) {
		int idLen = ByteArrayUtil.readInt(content);
		byte[] idB = ByteArrayUtil.subArray(content, 4, idLen);
		byte[] keyB = ByteArrayUtil.subArray(content, 4 + idLen);
		String id = this.stringSerializer.deserialize(idB);
		String key = this.stringSerializer.deserialize(keyB);

		return new PropertyKey(id, key);
	}

}
