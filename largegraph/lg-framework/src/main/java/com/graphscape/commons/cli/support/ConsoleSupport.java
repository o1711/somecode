package com.graphscape.commons.cli.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CliHandlerI;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.commons.cli.CommandType;
import com.graphscape.commons.cli.ConsoleI;
import com.graphscape.commons.cli.ConsoleReaderI;
import com.graphscape.commons.cli.ConsoleWriterI;
import com.graphscape.commons.cli.provider.DefaultConsoleReader;
import com.graphscape.commons.cli.provider.DefaultConsoleWriter;
import com.graphscape.commons.cli.provider.StackConsoleReader;
import com.graphscape.commons.cli.provider.StackConsoleReader.LineRead;
import com.graphscape.commons.cli.provider.StackConsoleWriter;
import com.graphscape.commons.configuration.ConfigurationI;
import com.graphscape.commons.handling.support.CollectionListener;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.lang.support.ConfigurableLifeCycleSupport;
import com.graphscape.commons.modulization.ComponentFactoryAwareI;
import com.graphscape.commons.modulization.ComponentFactoryI;
import com.graphscape.commons.util.TimeAndUnit;

/**
 * 
 * @author wuzhen
 * 
 */
public class ConsoleSupport<T extends ConsoleI> extends ConfigurableLifeCycleSupport implements ConsoleI,
		ComponentFactoryAwareI {

	private static final Logger LOG = LoggerFactory.getLogger(ConsoleSupport.class);

	private StackConsoleReader reader;

	private ExecutorService executor;

	private ComponentFactoryI componentFactory;

	private Semaphore running = new Semaphore(0);

	private CommandLineParser parser = new BasicParser();

	private Map<String, CommandType> commandMap = new HashMap<String, CommandType>();

	private CliHandlerI<T> handler;

	private StackConsoleWriter writer;

	private boolean pushStdInput = true;

	private boolean pushStdOutput = true;

	private String prompt = "$";

	private CollectionListener<CommandAndLine> commandPostInterceptor = new CollectionListener<CommandAndLine>();

	private int commandCounter;

	private char lineBreak = '\n';

	private boolean echo = true;

	TimeAndUnit timeout = TimeAndUnit.parse("60S");

	private boolean printLine;

	public ConsoleSupport(CliHandlerI<T> handler) {
		this.handler = handler;
		this.reader = new StackConsoleReader();
		this.writer = new StackConsoleWriter();
		this.executor = Executors.newSingleThreadExecutor();//
		this.addStateChangeHandler(StatusChange.valueOf(STARTING, RUNNING), new HandlerI<StatusChange>() {

			@Override
			public void handle(StatusChange t) {

				ConsoleSupport.this.running.release();

			}
		});

	}

	@Override
	public ConsoleI prompt(String p) {
		this.prompt = p;
		this.prompt();
		return this;
	}

	public void run() {
		try {
			this.runInternal();
		} catch (Throwable t) {
			// TODO exit
			LOG.error("todo exit", t);
		}
	}

	public void runInternal() {
		LOG.info("welcome to ...!");
		try {
			this.running.acquire();
		} catch (InterruptedException e) {
			throw new GsException(e);
		}
		LOG.info("... graph console!");
		while (this.isRunning()) {

			this.writer.write(this.prompt);

			LineRead line = this.reader.readLine();
			if (this.echo) {
				this.writer.writeLine(line.getLine());
			}

			this.processLine(this.commandCounter, line);

			this.commandCounter++;
		}
		LOG.warn("exiting graph console");

	}

	public void prompt() {
		this.writer.write(this.prompt);
	}

	public void processLine(int idx, LineRead lr) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("processing line:" + lr.getLine() + ",idx:" + idx);
		}

		CliContext<T> cc = buildCliContext(idx, lr);

		try {
			try {
				this.handler.handle(cc);
			} finally {
				if (cc.getErrorInfos().hasError()) {
					this.writer.writeLine(cc.getErrorInfos().getErrorCodeListAsString('\n'));
				}
				if (this.printLine) {
					this.writer.writeLine();
				}
			}
		} finally {
			this.commandPostInterceptor.handle(cc.getCommandLine());
		}

	}

	public CliContext<T> buildCliContext(int idx, LineRead lr) {

		String line = lr.getLine();
		String[] args = line.split(" ");
		String cmd = args[0];
		cmd = cmd.trim();
		CommandType command = this.commandMap.get(cmd);
		CommandLine cl = null;
		String[] args2 = null;
		if (command == null) {
			command = this.getHelpCommand();
			args2 = new String[] {};
		} else {
			args2 = new String[args.length - 1];
			System.arraycopy(args, 1, args2, 0, args2.length);
			cl = this.parseCommandLine(command, args2, false);
		}
		if (cl == null) {// not found the command or format error.
			command = this.getHelpCommand();
			args2 = new String[] { cmd };
			cl = this.parseCommandLine(command, args2, true);
		}
		CommandAndLine cnl = new CommandAndLine(this, idx, command, cl);
		CliContext<T> rt = new CliContext<T>(idx, (T) this, cnl, this.writer);

		return rt;
	}

	public CommandLine parseCommandLine(CommandType command, String[] args, boolean force) {

		CommandLine rt = null;
		try {

			Options options = command.getOptions();
			rt = this.parser.parse(options, args);

		} catch (ParseException e) {
			LOG.warn("parse error for cmd:" + command.getName(), e);
			if (force) {
				throw new GsException(e);
			}
		}
		return rt;
	}

	public CommandType getHelpCommand() {
		return this.commandMap.get("help");//
	}

	public void printHelp(Options options) {

	}

	@Override
	protected void doStart() {

		if (this.pushStdInput) {
			this.reader.push(new DefaultConsoleReader());
		}
		if (this.pushStdOutput) {
			this.writer.push(new DefaultConsoleWriter());
		}

		Future<String> fs = this.executor.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				ConsoleSupport.this.run();
				return null;

			}
		});

	}

	@Override
	protected void doShutdown() {

	}

	@Override
	public void config(ConfigurationI cfg) {
		super.config(cfg);

		List<ConfigurationI> ccfgL = this.config.getChildConfiguration("commands").getChildConfigurationList(
				"command");

		for (ConfigurationI ccfg : ccfgL) {
			// TODO change "cmd" to "name"
			String name = ccfg.getProperty("cmd", true);
			String desc = ccfg.getProperty("description", true);
			CommandType cmd = new CommandType(name, desc);
			for (ConfigurationI ocfg : ccfg.getChildConfigurationList("option")) {
				String opt = ocfg.getProperty("opt", true);
				String longOpt = ocfg.getProperty("longOpt", true);
				String hasArg = ocfg.getProperty("hasArgs", true);
				String description = ocfg.getProperty("description", true);
				cmd.addOption(opt, longOpt, Boolean.valueOf(hasArg), description);
			}
			this.commandMap.put(name, cmd);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.modulization.ComponentFactoryAwareI#
	 * setComponentFactory
	 * (com.graphscape.commons.modulization.ComponentFactoryI)
	 */
	@Override
	public void setComponentFactory(ComponentFactoryI cf) {
		this.componentFactory = cf;
	}

	@Override
	public ConsoleI pushReader(ConsoleReaderI cr) {
		return this.pushReader(cr, true);
	}

	@Override
	public ConsoleI pushReader(ConsoleReaderI cr, boolean popWhenClosed) {

		this.reader.push(cr, popWhenClosed);

		return this;
	}

	@Override
	public ConsoleI pushWriter(ConsoleWriterI cw) {
		this.writer.push(cw);
		return this;

	}

	@Override
	public ConsoleWriterI peekWriter() {
		return this.writer.peek();
	}

	@Override
	public ConsoleWriterI popWriter() {

		return this.writer.pop();
	}

	@Override
	public List<CommandType> getCommandList() {

		List<String> cmdL = new ArrayList<String>(this.commandMap.keySet());

		String[] names = cmdL.toArray(new String[cmdL.size()]);

		// sorted
		Arrays.sort(names);
		List<CommandType> rt = new ArrayList<CommandType>();
		for (int i = 0; i < names.length; i++) {
			CommandType cmd = this.commandMap.get(names[i]);
			rt.add(cmd);

		}

		return rt;
	}

	@Override
	public CommandType getCommand(String name) {
		// TODO Auto-generated method stub
		return this.commandMap.get(name);
	}

	@Override
	public void printLine(boolean printLine) {
		this.printLine = printLine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.cli.ConsoleI#addCommandPostInterceptor(com.graphscape
	 * .commons.lang.HandlerI)
	 */
	@Override
	public void addCommandPostInterceptor(HandlerI<CommandAndLine> handlerI) {
		this.commandPostInterceptor.addHandler(handlerI);

	}

}
