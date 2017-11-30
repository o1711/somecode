/**
 * Jan 14, 2014
 */
package com.graphscape.commons.file.provider.formaters;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.support.RootByteFormaterSupport;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.util.StringUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultByteFormater extends RootByteFormaterSupport {

	private boolean breakFirstLine;
	
	private static final String HEX = "hexList";
	
	private static final String SER = "serList";

	@Override
	protected void doFormat(PropertiesI<Object> context, ByteCursorI cur, byte[] content, Writer sb)
			throws IOException {
		String offset1 = ""+cur.getAbsoluteOffset();//
		
		ByteWindowI window = cur.getPointer().getWindow();

		String hex0 = window.getName();
		String hex2 = this.getAsHex(content);
		String hex1 = this.deserialize(window, content);

		int maxLength = StringUtil.maxLength(hex0, hex2, hex1);

		StringWriter list = this.getOrCreateHexWriter(HEX, context);
		list.append((offset1));
		list.append('[');
		list.append(StringUtil.fillSpace(hex2, maxLength));
		list.append(']' );

		StringWriter list2 = this.getOrCreateHexWriter(SER, context);
		list2.append(StringUtil.space(offset1));
		list2.append('[');
		list2.append(StringUtil.fillSpace(hex1, maxLength));
		list2.append(']' );

		sb.append(StringUtil.space(offset1));
		sb.write("[");
		sb.write(StringUtil.fillSpace(hex0, maxLength));
		sb.write("]" );

	}



	private StringWriter getOrCreateHexWriter(String name, PropertiesI<Object> context) {
		StringWriter list = (StringWriter) context.getProperty(name);
		if (list == null) {
			list = new StringWriter();
			context.setProperty(name, list);
			context.setProperty(name, list);
		}
		return list;
	}
	
	@Override
	protected void finishRootFormat(PropertiesI<Object> context,ByteCursorI cur, Writer sb) throws IOException {
		long offsetEnd = cur.getAbsoluteOffset()+cur.getLength();//the last one's end offset.
		
		
		StringWriter list2 = this.getOrCreateHexWriter(SER, context);
		sb.write("\n");
		sb.write(list2.toString());
		
		StringWriter list = this.getOrCreateHexWriter(HEX, context);
		sb.write("\n");
		sb.write(list.toString());
		sb.append(String.valueOf(offsetEnd));//

	}



	@Override
	protected void beforeRootFormat(int idx,PropertiesI<Object> context, ByteCursorI cur, Writer writer)
			throws IOException {
		if (this.breakFirstLine) {
			writer.append("\n");
		}

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
