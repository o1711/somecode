/**
 * Jan 5, 2014
 */
package com.graphscape.commons.record.provider.storage;

import com.graphscape.commons.record.RecordI;
import com.graphscape.commons.record.RecordType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class TxRecord implements RecordI {
	private RecordType recordType;
	private byte[] content;

	public TxRecord(RecordType rtype, byte[] content) {
		this.recordType = rtype;
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.RecordI#getType()
	 */
	@Override
	public RecordType getType() {
		// TODO Auto-generated method stub
		return recordType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.record.RecordI#getContent()
	 */
	@Override
	public byte[] getContent() {
		// TODO Auto-generated method stub
		return content;
	}

}
