package com.graphscape.commons.cli.provider;

import java.util.Stack;

import com.graphscape.commons.cli.ConsoleWriterI;
import com.graphscape.commons.lang.GsException;

public class StackConsoleWriter implements ConsoleWriterI {

	private Stack<ConsoleWriterI> stack = new Stack<ConsoleWriterI>();

	public StackConsoleWriter push(ConsoleWriterI rd) {
		this.stack.push(rd);
		return this;
	}

	public ConsoleWriterI peek() {
		return this.stack.peek();
	}

	public ConsoleWriterI pop() {
		return this.stack.pop();
	}

	@Override
	public ConsoleWriterI writeLine() {
		if (this.stack.isEmpty()) {
			return null;
		}

		ConsoleWriterI top = this.stack.peek();

		top.writeLine();

		return this;
	}

	@Override
	public ConsoleWriterI writeLine(String line) {
		if (this.stack.isEmpty()) {
			return null;
		}

		ConsoleWriterI top = this.stack.peek();

		top.writeLine(line);

		return this;
	}

	@Override
	public ConsoleWriterI write(String str) {
		if (this.stack.isEmpty()) {
			throw new GsException("writer stack is empty!");
		}
		ConsoleWriterI top = this.stack.peek();
		top.write(str);

		return this;
	}

}
