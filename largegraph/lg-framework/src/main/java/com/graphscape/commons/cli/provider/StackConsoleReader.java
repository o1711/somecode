package com.graphscape.commons.cli.provider;

import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.cli.ConsoleReaderI;
import com.graphscape.commons.handling.support.CollectionListener;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.Wrapper;
import com.graphscape.commons.util.TimeAndUnit;

public class StackConsoleReader {

	private static final Logger LOG = LoggerFactory.getLogger(StackConsoleReader.class);

	private Stack<ReaderWrapper> stack = new Stack<ReaderWrapper>();

	private CollectionListener<ConsoleReaderI> popHandler = new CollectionListener<ConsoleReaderI>();

	public static class LineRead {
		private String line;
		private ConsoleReaderI owner;

		public LineRead(ConsoleReaderI owner, String line) {
			this.owner = owner;
			this.line = line;
		}

		public String getLine() {
			return line;
		}

		public ConsoleReaderI getOwner() {
			return owner;
		}
	}

	private static class ReaderWrapper extends Wrapper<ConsoleReaderI> {

		private boolean popWhenClosed;

		private BlockingQueue<String> buffer = new LinkedBlockingQueue<String>();

		private ExecutorService executor = Executors.newSingleThreadExecutor();

		private Future<String> worker;

		private boolean produceFinished;

		private int producted;

		private int consumed;

		public ReaderWrapper(ConsoleReaderI t, boolean pop) {
			super(t);
			this.popWhenClosed = pop;
			this.worker = this.executor.submit(new Callable<String>() {

				@Override
				public String call() throws Exception {
					ReaderWrapper.this.run();
					return null;
				}
			});
		}

		protected void run() {

			while (true) {
				String line = this.target.readLine();
				if (line == null) {
					this.produceFinished = true;
					break;
				}

				try {
					this.buffer.put(line);
					this.producted++;
				} catch (InterruptedException e) {
					throw GsException.toRuntimeException(e);
				}
			}

		}

		private boolean isFinished() {
			return this.produceFinished && this.consumed == this.producted;
		}

		private String tryReadLine(TimeAndUnit timeout) {
			String rt = null;
			if (!this.isFinished()) {
				try {
					rt = buffer.poll(timeout.getValue(), timeout.getUnit());
					if (rt != null) {
						this.consumed++;
					}
				} catch (InterruptedException e) {
					throw GsException.toRuntimeException(e);
				}
			}

			return rt;

		}

	}

	public void addPopHandler(HandlerI<ConsoleReaderI> hdl) {
		this.popHandler.addHandler(hdl);

	}

	public StackConsoleReader push(ConsoleReaderI rd) {
		return this.push(rd, true);
	}

	public StackConsoleReader push(ConsoleReaderI rd, boolean popWhenClosed) {
		LOG.debug("push reader:" + rd);

		this.stack.push(new ReaderWrapper(rd, popWhenClosed));
		return this;
	}

	public LineRead readLine() {
		if (this.stack.isEmpty()) {
			return null;
		}
		LineRead rt = null;

		while (true) {
			ReaderWrapper top = this.stack.peek();
			if (top.isFinished()) {
				this.pop();
				continue;
			}

			String line = top.tryReadLine(TimeAndUnit.valueOf(1, TimeUnit.SECONDS));
			if (line == null) {
				continue;
			}

			rt = new LineRead(top.getTarget(), line);
			break;

		}

		return rt;
	}

	public void pop() {
		ConsoleReaderI cr = this.stack.pop().getTarget();
		this.popHandler.handle(cr);
	}

}
