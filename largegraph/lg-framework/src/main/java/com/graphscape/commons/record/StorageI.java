/**
 * Jan 2, 2014
 */
package com.graphscape.commons.record;

import com.graphscape.commons.lang.ResourceI;

/**
 * @author wuzhen0808@gmail.com <br>
 *         The logical unit to treat records.It simply translate the logical
 *         pointer to physical one to manipulate the underling segment.
 *         <p>
 *         the position space not jump.
 */
public interface StorageI extends ResourceI {

	public RecordI getRecord(Position position);

	public Position addRecord(RecordType type, byte[] content);

	public void updateRecord(Position position, RecordType type, byte[] content);

	public boolean removeRecord(Position position);

}
