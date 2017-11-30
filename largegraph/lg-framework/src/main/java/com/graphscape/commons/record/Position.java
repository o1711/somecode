/**
 * Jan 2, 2014
 */
package com.graphscape.commons.record;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class Position {

	private long pointer;

	public long getPointer() {
		return pointer;
	}

	public boolean isTemprary() {
		return this.pointer < 0;
	}

	public Position(long pointer) {
		this.pointer = pointer;
	}

	public static Position valueOf(long rtP) {
		return new Position(rtP);
	}

	@Override
	public int hashCode() {
		return (int) this.pointer;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Position)) {
			return false;
		}
		return ((Position) obj).pointer == this.pointer;

	}

}
