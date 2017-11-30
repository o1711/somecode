/**
 * Jan 5, 2014
 */
package com.graphscape.commons.record.provider.storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.graphscape.commons.record.Position;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class TxOperations {

	private Map<Position, TxOperation> opMap = new HashMap<Position, TxOperation>();

	public void put(Position position, TxOperation op) {
		this.opMap.put(position, op);
	}

	public TxOperation get(Position position) {
		return this.opMap.get(position);
	}

	public Iterator<TxOperation> iterator() {
		return this.opMap.values().iterator();
	}

	public void clear() {
		this.opMap.clear();
	}

}
