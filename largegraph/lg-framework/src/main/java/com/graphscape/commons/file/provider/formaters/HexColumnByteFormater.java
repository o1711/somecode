/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider.formaters;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.support.RootByteFormaterSupport;
import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class HexColumnByteFormater extends RootByteFormaterSupport {

	protected void doFormat(PropertiesI<Object> ctx, ByteCursorI cur, byte[] content, Writer sb)
			throws IOException {
		sb.append("[");

		for (int i = 0; i < content.length; i++) {
			// o/x
			sb.append(String.format("%02x", content[i] & 0xff));
		}
		// sb.append("Overflow(" + overflow + ")");

		sb.append("]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.file.support.ByteFormaterSupport#afterFormat(com
	 * .graphscape.commons.lang.PropertiesI,
	 * com.graphscape.commons.file.ByteWindowI, java.io.Writer)
	 */
	@Override
	protected void finishRootFormat(PropertiesI<Object> context, ByteCursorI cur, Writer sb)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeRootFormat(int idx,PropertiesI<Object> context, ByteCursorI cur, Writer writer)
			throws IOException {

		writer.append("ROOT WINDOW:[" + idx++);
		writer.append("]:");
		writer.append("\n");
	
	}

	@Override
	protected void afterAllFormated(int total, PropertiesI<Object> context, ByteCursorI cur, Writer writer)
			throws IOException {
		writer.append("\n");
		
	}
}