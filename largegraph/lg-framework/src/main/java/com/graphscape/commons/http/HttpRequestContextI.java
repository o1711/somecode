/**
 * 
 */
package com.graphscape.commons.http;

/**
 * @author wuzhen
 * 
 */
public interface HttpRequestContextI {

	public HttpRequestI getRequest();

	public HttpResponseI getResponse();

	public HttpResponseI response();

	public HttpResponseI response(int status);

	public void endOfResponse();
	
	public void endOfResponse(boolean close);

}
