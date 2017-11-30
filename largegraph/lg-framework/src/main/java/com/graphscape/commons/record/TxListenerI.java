/**
 * Jan 5, 2014
 */
package com.graphscape.commons.record;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface TxListenerI {
	/**
	 * position my be temp one, and it will be changed when commit. user may
	 * need this event to update their index.
	 * 
	 * @param oldPos
	 * @param newPos
	 */
	public void onRecordPositionChanged(Position oldPos, Position newPos);

}
