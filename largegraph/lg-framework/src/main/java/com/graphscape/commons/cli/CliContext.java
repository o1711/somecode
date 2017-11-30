/**
 * Dec 19, 2013
 */
package com.graphscape.commons.cli;

import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.support.HasAttributeSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CliContext<T extends ConsoleI> extends HasAttributeSupport {

	private CommandAndLine commandLine;

	private T console;

	private int index;

	private ConsoleWriterI writer;

	private ErrorInfos errorInfos = new ErrorInfos();

	public CliContext(int idx, T console, CommandAndLine cmdL, ConsoleWriterI writer) {
		this.commandLine = cmdL;
		this.console = console;
		this.index = idx;
		this.writer = writer;
	}

	public CommandAndLine getCommandLine() {
		return commandLine;
	}

	public void write(String str) {
		this.writer.write(str);
	}

	public void writeLine() {
		this.writer.writeLine();
	}

	public void writeLine(String line) {
		this.writer.writeLine(line);
	}

	public T getConsole() {
		return console;
	}

	public int getIndex() {
		return index;
	}

	public ErrorInfos getErrorInfos() {
		return errorInfos;
	}
}
