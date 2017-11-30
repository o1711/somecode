/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file;

import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.HasAttributeI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ByteCursorI extends ByteReadableI, ByteWritableI, HasAttributeI {

	public boolean isWindow(ByteWindowI window);

	public PlainRegionI getRegion();

	public BytePosition getPointer();

	public ByteCursorI parent();

	public ByteCursorI root();

	public ByteCursorI left();

	public ByteCursorI left(String name, boolean force);

	// only this method case new field to be created.
	public ByteCursorI right();

	public ByteCursorI right(int step);

	public ByteCursorI firstBrother();

	public long getAbsoluteOffset();// the offset, relative to the root.

	public long getLength();

	public ByteCursorI firstChild();

	public ByteCursorI lastChild();

	public ByteCursorI firstLeaf();

	public boolean hasNext();

	public boolean isFirstChild();

	public boolean isLastChild();

	// save current status.
	public ByteCursorI push();

	// restore saved status.
	public ByteCursorI pop();

	public <T> T pushAndPop(CallbackI<ByteCursorI, T> call);

	public byte[] read();

	public ByteCursorI next();

}
