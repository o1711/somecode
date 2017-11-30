/**
 * Dec 19, 2013
 */
package com.graphscape.commons.cli;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ConsoleWriterI {
	
	public ConsoleWriterI writeLine();	
	
	public ConsoleWriterI write(String str);
	
	public ConsoleWriterI writeLine(String line);
	
}
