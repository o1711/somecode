/**
 * Jan 16, 2014
 */
package com.graphscape.commons.file.provider.formaters;

import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.support.LeafByteFormaterSupport;
import com.graphscape.commons.record.SerializerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class SerializerFormater extends LeafByteFormaterSupport {

	private String format;

	public SerializerFormater() {

	}

	public SerializerFormater(int widthJustify, boolean rightAlgn, String surfix) {
		super(widthJustify, rightAlgn, surfix);
	}

	public SerializerFormater(String format) {
		this.format = format;
	}

	@Override
	protected void formatInternal(ByteCursorI cur, StringBuffer sb) {
		SerializerI ser = cur.getPointer().getWindow().getSerializer();
		if (ser == null) {
			sb.append(this.getAsHex(cur));
		} else {
			Object obj = ser.deserialize(cur.read());
			String str = this.format == null ? String.valueOf(obj) : String.format(format, obj);
			sb.append(str);//
		}
	}

}
