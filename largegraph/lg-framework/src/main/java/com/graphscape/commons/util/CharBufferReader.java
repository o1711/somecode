/**
 * Dec 14, 2013
 */
package com.graphscape.commons.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CharBufferReader extends Reader {
	private CharBuffer buffer;

	public CharBufferReader(CharBuffer cbuf) {
		this.buffer = cbuf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Reader#read(char[], int, int)
	 */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int rem = buffer.remaining();
		if (rem == 0) {
			return -1;
		}
		int rt = Math.min(rem, len);
		buffer.get(cbuf, off, rt);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Reader#close()
	 */
	@Override
	public void close() throws IOException {
		
	}

}
