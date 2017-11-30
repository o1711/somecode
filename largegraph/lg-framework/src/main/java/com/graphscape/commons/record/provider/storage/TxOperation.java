/**
 * Jan 5, 2014
 */
package com.graphscape.commons.record.provider.storage;

import com.graphscape.commons.record.Position;
import com.graphscape.commons.record.RecordType;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class TxOperation {
	public static final int ADD = 1;
	public static final int REMOVE = 2;
	public static final int UPDATE = 3;
	public static final int LOAD = 4;

	private Position position;

	private int type;

	private RecordType recordType;

	private byte[] content;

	private TxOperation(Position pos, int type, RecordType rtype, byte[] content) {
		this.position = pos;
		this.type = type;
		this.recordType = rtype;
		this.content = content;
	}

	public static TxOperation valueOf(Position pos, int type, RecordType rtype, byte[] content) {

		return new TxOperation(pos, type, rtype, content);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void update(int type, RecordType rtype, byte[] content) {
		this.type = type;
		this.recordType = rtype;
		this.content = content;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
