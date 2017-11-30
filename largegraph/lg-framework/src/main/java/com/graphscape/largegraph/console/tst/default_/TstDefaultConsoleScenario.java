package com.graphscape.largegraph.console.tst.default_;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.commons.concurrent.FutureI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.support.ThreadWorkerSupport;
import com.graphscape.commons.util.ClassLoaderUtil;
import com.graphscape.commons.util.TimeAndUnit;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.tst.TstConsoleScenarioI;
import com.graphscape.largegraph.console.tst.TstScenarioResult;
import com.graphscape.largegraph.console.tst.TstStatement;
import com.graphscape.largegraph.console.tst.statement.CommandStatement;
import com.graphscape.largegraph.console.tst.statement.EndStatement;
import com.graphscape.largegraph.console.tst.statement.PlainOutputStatement;
import com.graphscape.largegraph.console.tst.statement.StartStatement;

public class TstDefaultConsoleScenario extends ThreadWorkerSupport<TstScenarioResult> implements
		TstConsoleScenarioI {

	private static final Logger LOG = LoggerFactory.getLogger(TstDefaultConsoleScenario.class);

	String res;

	protected LargeGraphConsoleI console;

	protected List<CommandStatement> commandStatementList = new ArrayList<CommandStatement>();

	protected TstStatement startStatement;

	protected TstStatement endStatement;

	protected String prompt;

	protected TstScenarioConsoleReader reader;

	protected TstScenarioConsoleWriter writer;

	protected TimeAndUnit timeout = TimeAndUnit.valueOf(1, TimeUnit.MINUTES);

	protected boolean printLine;

	public TstDefaultConsoleScenario(LargeGraphConsoleI console, String res) {
		this.console = console;
		this.res = res;
	}

	public TstStatement getStatement(int ln) {
		return this.getStatement(ln);
	}

	public List<CommandStatement> getCommandStatementList() {
		return this.commandStatementList;
	}

	public TstStatement getCommandStatement(int ln, boolean force) {
		TstStatement rt = this.commandStatementList.get(ln);
		if (rt == null && force) {
			throw new GsException("no statement with line number:" + ln);
		}
		return rt;

	}

	public void loadStatements() {
		LOG.debug("loading scenario statement from:" + res + "...");

		InputStream is = ClassLoaderUtil.getResourceAsStream(this.res, true);
		LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
		int ln = 0;

		int index = 0;
		CommandStatement cs = null;
		while (true) {

			String line = null;
			try {
				line = reader.readLine();
			} catch (IOException e) {
				throw new GsException(e);
			}
			if (line == null) {
				break;
			}

			if (line.startsWith("!")) {
				if (line.startsWith("!prompt=")) {
					this.prompt = line.substring("!prompt=".length());
				} else if (line.startsWith("!print-line")) {
					this.printLine = true;
				} else if (line.startsWith("!start")) {
					this.startStatement = new StartStatement(this, ln, line);
				} else if (line.startsWith("!end")) {
					this.endStatement = new EndStatement(this, ln, line);
				} else {
					throw new GsException("no this instruct:" + line);
				}
			} else {// not !
				if (this.startStatement == null) {// before state line.
					// as comment.
				} else {
					if (this.prompt == null) {// not instruct prompt.
						throw new GsException("please !prompt:somestring before !start");
					}

					if (line.startsWith(this.prompt)) {
						cs = new CommandStatement(this, ln, line, this.prompt, index);
						this.commandStatementList.add(cs);//
						LOG.debug("command statement:" + cs + ",ln:" + ln + ",idx:" + index);
						index++;
					} else {

						PlainOutputStatement os = new PlainOutputStatement(this, ln, line, cs);
						cs.getOutputStatementList().add(os);
					}

				}

			}
			ln++;
		}

		LOG.debug("done of loading scenario statement,total command:" + this.commandStatementList.size());
	}

	@Override
	public TstScenarioResult call() {
		LOG.debug("start running scenario,res:" + res);

		TstScenarioResult rt = new TstScenarioResult();

		this.loadStatements();// load command script from file
		this.reader = new TstScenarioConsoleReader(this, this.commandStatementList);
		this.writer = new TstScenarioConsoleWriter(this.console, this, rt);
		FutureI<TstScenarioResult> rtF = this.writer.submit();
		console.printLine(this.printLine);
		console.pushWriter(this.writer);
		console.pushReader(this.reader);

		console.addCommandPostInterceptor(new HandlerI<CommandAndLine>() {

			@Override
			public void handle(CommandAndLine t) {
				TstDefaultConsoleScenario.this.onCommandProcessed(t);
			}
		});
		this.console.prompt(this.prompt);
		try {
			rtF.get(timeout);
		} catch (Exception e) {

			rt.getErrorInfos().add(e);//

		}

		return rt;
	}

	/**
	 * @param t
	 */
	protected void onCommandProcessed(CommandAndLine t) {
		if (t.getIndex() == this.commandStatementList.size() - 1) {// finished.
			this.writer.close();
		}
	}

	@Override
	public int getStartLineNumber() {
		return this.startStatement.getLineNumber();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.largegraph.console.tst.TstConsoleScenarioI#
	 * getCommandStatementByIndex(int)
	 */
	@Override
	public CommandStatement getCommandStatementByIndex(int index) {
		return this.commandStatementList.get(index);

	}

}
