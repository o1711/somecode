/**
 * Dec 21, 2013
 */
package com.graphscape.largegraph.test.other;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class CmdTest {

	public static void main(String[] args) {
		args = new String[]{"-h","H","-u","U"};
		args = new String[]{"-u","User","-dsn","Dsn"};
		
		try {
			Options opts = new Options();
			opts.addOption("h", false, "Print help for this application");
			opts.addOption("u", true, "The username to use");
			opts.addOption("dsn", true, "The data source to use");
			BasicParser parser = new BasicParser();
			CommandLine cl = parser.parse(opts, args);

			if (cl.hasOption('h')) {
				HelpFormatter hf = new HelpFormatter();
				hf.printHelp("OptionsTip", opts);
			} else {
				System.out.println(cl.getOptionValue("u"));
				System.out.println(cl.getOptionValue("dsn"));
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
		}

	}

}
