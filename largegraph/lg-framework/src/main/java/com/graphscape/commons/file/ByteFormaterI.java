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
public interface ByteFormaterI {

	public void format(ByteCursorI cur, Writer writer);

	public void format(ByteCursorI cur, PrintStream ps);
	
}
