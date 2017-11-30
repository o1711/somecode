/**
 * Jan 4, 2014
 */
package com.graphscape.commons.record.provider;

import com.graphscape.commons.record.RecordI;
import com.graphscape.commons.record.RecordType;
import com.graphscape.commons.record.spi.InternalPosition;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultRecord implements RecordI {
	private InternalPosition internalPosition;

	private byte[] content;

	/**
	 * @param ip
	 * @param content
	 */
	public DefaultRecord(InternalPosition ip, byte[] content) {
		this.internalPosition = ip;
		this.content = content;
	}

	@Override
	public RecordType getType() {
		return RecordType.valueOf(this.internalPosition.getType());
	}

	@Override
	public byte[] getContent() {
		return this.content;
	}

}
