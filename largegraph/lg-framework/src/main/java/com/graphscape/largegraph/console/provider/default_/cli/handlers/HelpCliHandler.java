/**
 * Dec 19, 2013
 */
package com.graphscape.largegraph.console.provider.default_.cli.handlers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;

import com.graphscape.commons.cli.CliContext;
import com.graphscape.commons.cli.CommandAndLine;
import com.graphscape.commons.cli.CommandType;
import com.graphscape.largegraph.console.LargeGraphConsoleI;
import com.graphscape.largegraph.console.support.CliHandlerSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class HelpCliHandler extends CliHandlerSupport {

	@Override
	protected void handleInternal(CliContext<LargeGraphConsoleI> cc) {

		// HelpFormatter formatter = new HelpFormatter();
		// formatter.printHelp("help", cc.getCommand().getOptions());
		CommandAndLine cl = cc.getCommandLine();
		String[] as = cl.getArgs();
		if (as.length == 1) {
			String cname = as[0];
			CommandType cmd = cc.getConsole().getCommand(cname);
			if (cmd == null) {
				cc.getErrorInfos().add("not found command:" + cname);
				return;
			}
			HelpFormatter formatter = new HelpFormatter();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			formatter
					.printHelp(pw, 1100, cname, cmd.getDescription(), cmd.getOptions(),2, 6, "", true);
			cc.write(sw.getBuffer().toString());
		} else {
			this.printHelpItSelf(cc);

		}

	}

	protected void printHelpItSelf(CliContext cc) {
		cc.writeLine("usage: help <command>");
		cc.writeLine();
		cc.writeLine("The most commonly used commands are:");

		List<CommandType> cmdL = cc.getConsole().getCommandList();
		int maxLength = this.getMaxLength(cmdL);
		for (int i = 0; i < cmdL.size(); i++) {
			CommandType cmd = cmdL.get(i);
			String name = cmd.getName();
			cc.write(name);
			for (int x = name.length(); x < maxLength; x++) {
				cc.write(" ");
			}
			cc.write(" ");
			cc.write(cmd.getDescription());
			cc.writeLine();
		}

	}

	private int getMaxLength(List<CommandType> cmdL) {
		int rt = 0;

		for (CommandType c : cmdL) {
			int l = c.getName().length();
			if (rt < l) {
				rt = l;
			}
		}
		return rt;
	}

}
