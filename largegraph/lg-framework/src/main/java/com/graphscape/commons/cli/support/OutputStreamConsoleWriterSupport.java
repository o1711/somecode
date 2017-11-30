package com.graphscape.commons.cli.support;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.graphscape.commons.lang.GsException;

public class OutputStreamConsoleWriterSupport extends ConsoleWriterSupport {

	private Writer target;

	public OutputStreamConsoleWriterSupport(OutputStream os) {
		this.target = new OutputStreamWriter(os);
	}

	@Override
	public void writeInternal(String str) {
		try {
			this.target.write(str, 0, str.length());
		} catch (IOException e) {
			throw new GsException(e);
		}
	}

}
