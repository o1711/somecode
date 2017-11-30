/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface HttpResponseI extends HttpMessageI {

	public void write();

	public void write(String content);

	public void setContentType(String string);

}
