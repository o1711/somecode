/**
 * Dec 19, 2013
 */
package com.graphscape.commons.cli.provider;

import com.graphscape.commons.cli.support.OutputStreamConsoleWriterSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultConsoleWriter extends OutputStreamConsoleWriterSupport {

	public DefaultConsoleWriter() {
		super(System.out);
	}

}
