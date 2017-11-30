package com.graphscape.commons.record.provider.segments.tree.formaters;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.support.LeafByteFormaterSupport;
import com.graphscape.commons.record.provider.segments.tree.DefaultBinaryTreeSegment;

public class TypeFormater extends LeafByteFormaterSupport {
	/**
	 * @param format
	 */
	public TypeFormater() {
		super();
		// TODO Auto-generated constructor stub
	}

	private int maxkWidth = 10;

	@Override
	public void formatInternal(ByteCursorI cur, Writer sb) throws IOException {
		int type = cur.readInt(0);
		switch (type) {
		case DefaultBinaryTreeSegment.LEFT:
			sb.write("L");
			break;
		case DefaultBinaryTreeSegment.RIGHT:
			sb.write("R");
			break;
		case DefaultBinaryTreeSegment.ROOT:
			sb.write("O");
			break;
		default:
			sb.write("N");
		}

	}

}