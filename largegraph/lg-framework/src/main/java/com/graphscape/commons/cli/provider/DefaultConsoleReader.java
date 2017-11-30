/**
 * Dec 19, 2013
 */
package com.graphscape.commons.cli.provider;

import com.graphscape.commons.cli.support.InputStreamConsoleReaderSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultConsoleReader extends InputStreamConsoleReaderSupport {

	public DefaultConsoleReader() {
		super(System.in);
	}

}
