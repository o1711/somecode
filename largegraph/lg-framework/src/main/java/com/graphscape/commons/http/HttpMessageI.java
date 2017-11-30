/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface HttpMessageI {
	public void setHeader(String key, String value);

	public String getHeader(String key);

	public String getCharset();

	public String getContentType();

	public long getContentLength();
	
	public void setContentType(String string);

}
