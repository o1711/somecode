package com.graphscape.commons.cli.support;

import com.graphscape.commons.cli.ConsoleWriterI;

public abstract class ConsoleWriterSupport implements ConsoleWriterI {

	public static String LBK = "\r\n";

	public ConsoleWriterSupport() {
	}

	@Override
	public ConsoleWriterI writeLine(String line) {
		return this.write(line + LBK);
	}

	@Override
	public ConsoleWriterI write(String str) {
		this.writeInternal(str);
		return this;
	}

	protected abstract void writeInternal(String str);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.console.ConsoleWriterI#writeLine()
	 */
	@Override
	public ConsoleWriterI writeLine() {

		return this.write(LBK);

	}

}
