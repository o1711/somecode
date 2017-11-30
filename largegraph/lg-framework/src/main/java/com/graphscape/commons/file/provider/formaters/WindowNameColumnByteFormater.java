/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider.formaters;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.support.RootByteFormaterSupport;
import com.graphscape.commons.lang.PropertiesI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class WindowNameColumnByteFormater extends RootByteFormaterSupport {

	protected void doFormat(PropertiesI<Object> ctx, ByteCursorI cur, byte[] content, Writer sb)
			throws IOException {
		ByteWindowI window = cur.getPointer().getWindow();
		sb.append("[");
		sb.append(window.getName());
		sb.append("]");
	}

	@Override
	protected void finishRootFormat(PropertiesI<Object> context, ByteCursorI cur, Writer sb)
			throws IOException {

	}

	@Override
	protected void beforeRootFormat(int idx, PropertiesI<Object> context, ByteCursorI cur, Writer writer)
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