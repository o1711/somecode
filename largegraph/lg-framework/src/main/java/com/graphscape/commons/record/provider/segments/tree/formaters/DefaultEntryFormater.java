package com.graphscape.commons.record.provider.segments.tree.formaters;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.LeafByteFormaterI;
import com.graphscape.commons.file.support.RootByteFormaterSupport;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.record.provider.index.rbtree.BiTreeSegmentProxySupport.Direct;
import com.graphscape.commons.util.StringUtil;

public class DefaultEntryFormater extends RootByteFormaterSupport {

	protected int maxDepth;
	protected int depth;

	public DefaultEntryFormater(int depth, int maxDepth) {
		this.depth = depth;
		this.maxDepth = maxDepth;
	}

	@Override
	protected void doFormat(PropertiesI<Object> ctx, ByteCursorI cur, byte[] content, Writer sb)
			throws IOException {
		ByteWindowI window = cur.getPointer().getWindow();
		LeafByteFormaterI fmt = window.getFormater();

		boolean isTypeField = window.getName().equals("DIRECT");
		if (isTypeField) {//
			sb.write(StringUtil.space(this.depth + 1));// SPACE

		}
		if (fmt == null) {
			sb.write("[");
			sb.write(this.getAsHex(content));//
			sb.write("]");
		} else {
			fmt.format(cur, sb);//
		}

		if (isTypeField) {//
			sb.write(StringUtil.space(this.maxDepth - this.depth + 1));//
		}

	}

	@Override
	protected void finishRootFormat(PropertiesI<Object> context, ByteCursorI cur, Writer sb)
			throws IOException {

	}

	@Override
	protected void beforeRootFormat(int idx, PropertiesI<Object> context, ByteCursorI cur, Writer sb)
			throws IOException {

	}

	@Override
	protected void afterAllFormated(int total, PropertiesI<Object> context, ByteCursorI cur, Writer writer)
			throws IOException {
		writer.write("\n");
	}

}