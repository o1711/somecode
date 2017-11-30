package com.graphscape.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.concurrent.FiniteBlockingQueueI;
import com.graphscape.commons.concurrent.provider.LinkedFiniteBlockingQueue;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.support.ThreadWorkerSupport;

public class StringLineQueue extends ThreadWorkerSupport<Object> {
	private static final Logger LOG = LoggerFactory.getLogger(StringLineQueue.class);

	public FiniteBlockingQueueI<String> buffer = new LinkedFiniteBlockingQueue<String>();

	private Reader target;

	private char lineBrk;

	private long lines;

	private boolean readOut;

	public StringLineQueue(InputStream is, char lineBrk) {
		this(new InputStreamReader(is), lineBrk);
	}

	public StringLineQueue(Reader r, char lineBrk) {
		this.target = r;
		this.lineBrk = lineBrk;
	}

	@Override
	protected Object call() {

		try {
			return this.callInternal();
		} catch (Exception e) {
			throw GsException.toRuntimeException(e);
		}
	}

	public String readLine() {
		this.submitIfNotYet();
		try {
			String rt = this.buffer.take();
			return rt;
		} catch (NoSuchElementException e) {
			return null;//end of queue.
		}
	}

	protected Object callInternal() throws Exception {
		while (true) {

			String s = this.nextLine();
			if (s == null) {
				buffer.close();//NOTE,must call this to avoid dead lock.
				break;// end
			}
			buffer.put(s);
			this.lines++;

		}
		return this.lines;
	}

	protected String nextLine() throws IOException {
		StringBuffer sb = null;
		while (!this.readOut) {// read line

			int i = this.target.read();
			if (i == -1) {
				this.readOut = true;
				break;
			}

			if (sb == null) {
				sb = new StringBuffer();
			}
			char c = (char) i;
			if (c == this.lineBrk) {// is end of line,break to return.
				break;
			}
			sb.append(c);

		}
		if (sb == null) {
			return null;
		}
		String s = sb.toString();
		return s;
	}

}
