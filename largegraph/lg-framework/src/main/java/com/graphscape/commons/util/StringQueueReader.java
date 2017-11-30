package com.graphscape.commons.util;

import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;

import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.concurrent.provider.LinkedFiniteBlockingQueue;
import com.graphscape.commons.lang.GsException;

public class StringQueueReader extends Reader {

	public FiniteBlockingQueueI<String> buffer = new LinkedFiniteBlockingQueue<String>();

	private String lastReadRemain;

	public StringQueueReader() {

	}

	//No more
	public void finish() {
		this.buffer.close();
	}

	public void put(String s) {
		if (s.length() == 0) {
			throw new GsException("cannot write empty string.");
		}
		this.buffer.put(s);

	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {

		if (len == 0) {
			throw new GsException("length is zero");
		}

		int rt = 0;
		while (true) {

			if (len == 0) {// max len is copied.
				break;
			}

			String str = null;
			if (this.lastReadRemain == null) {// no remain from last read,to
												// see if any in queue.
				try {
					str = this.buffer.take();
				} catch (NoSuchElementException e) {
					rt = -1;//end of queue,end of reader
					break;
				}

			} else {
				str = this.lastReadRemain;
				this.lastReadRemain = null;
			}

			int lenCopy = Math.min(len, str.length());// len to copy to
														// cbuf.

			// copy char to cbuf.
			str.getChars(0, lenCopy, cbuf, off);
			// total:
			rt += lenCopy;
			// off:
			off = off + lenCopy;
			// len
			len = len - lenCopy;

			int lenRemain = str.length() - lenCopy;// len of remained.
			if (lenRemain > 0) {// cbuf if full,must reamin sub string.
				this.lastReadRemain = str.substring(lenCopy);//
				break;
			}

		}
		return rt;

	}

	@Override
	public void close() throws IOException {
	}
}
