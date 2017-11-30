package com.graphscape.commons.cli.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.graphscape.commons.cli.ConsoleReaderI;
import com.graphscape.commons.lang.GsException;

public class InputStreamConsoleReaderSupport implements ConsoleReaderI {

	protected Reader target;

	public InputStreamConsoleReaderSupport(InputStream t) {
		this(new InputStreamReader(t));
	}

	public InputStreamConsoleReaderSupport(InputStreamReader t) {
		this.target = t;
	}

	@Override
	public String readLine() {
		
		StringBuffer sb = new StringBuffer();
		while (true) {

			try {
				char c = (char) target.read();
				if (c == '\n') {
					break;
				}
				sb.append(c);
			} catch (IOException e) {
				throw new GsException(e);
			}
		}
		return sb.toString();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
	

}
