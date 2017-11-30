package com.graphscape.largegraph.console.tst.default_;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.cli.ConsoleI;
import com.graphscape.commons.cli.ConsoleWriterI;
import com.graphscape.commons.cli.support.ConsoleWriterSupport;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.WorkerI;
import com.graphscape.commons.lang.provider.default_.DefaultWorker;
import com.graphscape.commons.util.StringLineQueue;
import com.graphscape.commons.util.StringQueueReader;
import com.graphscape.largegraph.console.tst.TstConsoleScenarioI;
import com.graphscape.largegraph.console.tst.TstScenarioResult;
import com.graphscape.largegraph.console.tst.statement.CommandStatement;
import com.graphscape.largegraph.console.tst.statement.TstOutputStatement;

public class TstScenarioConsoleWriter extends ConsoleWriterSupport implements WorkerI<TstScenarioResult>,
		Callable<TstScenarioResult> {

	private static final Logger LOG = LoggerFactory.getLogger(TstScenarioConsoleWriter.class);

	TstConsoleScenarioI scenario;

	private int lineNumber;

	private StringLineQueue lineQueue;

	private StringQueueReader queueReader;

	private TstScenarioResult result;

	private List<TstOutputStatement> expectedOutputList;

	private List<String> historyList = new ArrayList<String>();

	private Map<Integer, ErrorInfos> errorInfosMap = new HashMap<Integer, ErrorInfos>();
	private ConsoleI console;

	private boolean closed;

	public TstScenarioConsoleWriter(ConsoleI console, TstConsoleScenarioI scenario, TstScenarioResult rest) {
		this.console = console;
		this.scenario = scenario;

		this.result = rest;
		queueReader = new StringQueueReader();
		this.lineQueue = new StringLineQueue(queueReader, this.LBK.charAt(1));//
		this.expectedOutputList = new ArrayList<TstOutputStatement>();
		List<CommandStatement> csL = this.scenario.getCommandStatementList();

		for (int i = 0; i < csL.size(); i++) {
			CommandStatement cs = csL.get(i);
			expectedOutputList.add(cs);
			expectedOutputList.addAll(cs.getOutputStatementList());//
		}

	}

	/**
	 * Call close to close.Means no more input line from now on. The reader will
	 * return null after this is called.
	 */
	public void close() {
		if (this.closed) {
			throw new GsException("already closed.");
		}
		ConsoleWriterI cw = this.console.peekWriter();
		if (cw != this) {
			throw new GsException("console peek writer not this one,what's wrong?");
		}
		this.console.popWriter();
		this.queueReader.finish();// finish the reader,no more char .
		this.closed = true;
	}

	@Override
	public void writeInternal(String str) {
		// if (LOG.isTraceEnabled()) {
		// LOG.trace("str:" + str);
		// }
		this.queueReader.put(str);
	}

	@Override
	public TstScenarioResult call() {

		while (true) {
			String line = this.lineQueue.readLine();
			if (line == null) {
				break;
			}
			this.onLineOutput(lineNumber, line);

			this.lineNumber++;

		}
		this.onLineOutputEnd();
		return this.result;
	}

	/**
	 * @param lineNumber2
	 * @param line
	 */
	private void onLineOutput(int ln, String line) {
		if (LOG.isTraceEnabled()) {
			//LOG.trace(ln + "\t:" + line);
		}

		this.historyList.add(line);

		if (ln >= this.expectedOutputList.size()) {
			this.result.getErrorInfos().add("unexpected line number:" + ln + ",line:" + line);
			return;
		}

	}

	/**
	 * @param lineNumber2
	 */
	private void onLineOutputEnd() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("output end,total:" + this.lineNumber);

		}
		if (this.lineNumber < this.expectedOutputList.size()) {
			this.result.getErrorInfos().add(
					"underflow of output,expected lines:" + this.expectedOutputList.size() + ",actual size:"
							+ this.lineNumber);
		}
		if (LOG.isTraceEnabled()) {// print expected

			StringBuffer sb = new StringBuffer();
			sb.append("<expected-output>\n");
			for (int i = 0; i < this.expectedOutputList.size(); i++) {
				sb.append("" + i + "\t" + this.expectedOutputList.get(i).getValue());
				sb.append("\n");
			}
			sb.append("</expected-output>\n");

			LOG.trace(sb.toString());
		}

		if (LOG.isTraceEnabled()) {
			StringBuffer sb = new StringBuffer();
			sb.append("<actural-output>\n");
			for (int i = 0; i < this.historyList.size(); i++) {

				String value = this.historyList.get(i);
				String mark = "";
				if (i < this.expectedOutputList.size()) {
					TstOutputStatement sI = expectedOutputList.get(i);
					if (!sI.isMatch(value)) {
						mark = "*";
					}
				} else {
					mark = "+";
				}
				sb.append("" + i + mark + "\t" + value);

				sb.append("\n");
			}
			sb.append("</actural-output>\n");
			LOG.trace(sb.toString());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.lang.WorkerI#submit()
	 */
	@Override
	public FutureI<TstScenarioResult> submit() {
		WorkerI<TstScenarioResult> target = new DefaultWorker<TstScenarioResult>(this);
		return target.submit();
	}

}
