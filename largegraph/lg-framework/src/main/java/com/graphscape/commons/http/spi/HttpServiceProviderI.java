/**
 * Dec 12, 2013
 */
package com.graphscape.commons.http.spi;

import com.graphscape.commons.http.HttpRequestHandlerI;
import com.graphscape.commons.http.HttpRequestI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface HttpServiceProviderI {

	public HttpRequestHandlerI resolveHttpRequestHandler(HttpRequestI req);

}
