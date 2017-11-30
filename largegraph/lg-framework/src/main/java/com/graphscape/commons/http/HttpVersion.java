/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class HttpVersion {

	public static final HttpVersion HTTP_1_0 = new HttpVersion("HTTP/1.0");
	public static final HttpVersion HTTP_1_1 = new HttpVersion("HTTP/1.1");

	private String version;

	private HttpVersion(String v) {
		this.version = v;
	}

	public static HttpVersion valueOf(String value) {
		return new HttpVersion(value);
	}
}
