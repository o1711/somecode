package com.graphscape.commons.file.support;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.LeafByteFormaterI;
import com.graphscape.commons.util.StringUtil;

public abstract class LeafByteFormaterSupport extends ByteFormaterSupport implements LeafByteFormaterI {
	protected boolean rightAlgin;
	protected int widthJustify;

	protected String surfix;

	public LeafByteFormaterSupport() {

	}

	public LeafByteFormaterSupport(int widthJustify, boolean rightAlgin, String surfix) {
		this.rightAlgin = rightAlgin;
		this.widthJustify = widthJustify;
		this.surfix = surfix;
	}

	@Override
	protected void formatInternal(ByteCursorI cur, Writer writer) throws IOException {

		StringBuffer sb = new StringBuffer();
		this.formatInternal(cur, sb);

		String str = sb.toString();

		if (widthJustify > 0) {
			if (this.rightAlgin) {
				str = StringUtil.fillSpace(this.widthJustify, str);
			} else {
				str = StringUtil.fillSpace(str, this.widthJustify);
			}
		}
		writer.write(str);
		if (this.surfix != null) {
			writer.write(surfix);
		}

	}

	protected void formatInternal(ByteCursorI cur, StringBuffer sb) throws IOException {

	}

}
