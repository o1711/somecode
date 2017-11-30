/**
 * Jan 14, 2014
 */
package com.graphscape.commons.file;

import java.io.PrintStream;
import java.io.Writer;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface RootByteFormaterI extends ByteFormaterI{

	public void format(ByteWindowI window, PlainRegionI region, Writer writer);

	public void format(ByteWindowI window, PlainRegionI region, PrintStream ps);

	public void format(boolean alsoFormatNext, ByteCursorI cur, Writer writer);

	public void format(boolean alsoFormatNext, ByteCursorI cur, PrintStream ps);

	public void format(boolean alsoFormatNext, ByteWindowI window, PlainRegionI region, Writer writer);

	public void format(boolean alsoFormatNext, ByteWindowI window, PlainRegionI region, PrintStream ps);

}
