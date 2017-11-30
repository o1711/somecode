/**
 * Jan 16, 2014
 */
package com.graphscape.commons.record.provider.index.rbtree.formaters;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.support.LeafByteFormaterSupport;
import com.graphscape.commons.record.provider.index.rbtree.RedBlackTreeIndex;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ColorFormater extends LeafByteFormaterSupport {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.file.support.LeafByteFormaterSupport#formatInternal
	 * (com.graphscape.commons.file.ByteCursorI, java.io.Writer)
	 */
	@Override
	protected void formatInternal(ByteCursorI cur, Writer writer) throws IOException {
		byte color = cur.readByte(0);

		if (color == RedBlackTreeIndex.RED) {
			writer.append("R");
		} else if (color == RedBlackTreeIndex.BLACK) {
			writer.append("B");
		} else {
			writer.append(this.getAsHex(new byte[] { color }));
		}
	}

}
