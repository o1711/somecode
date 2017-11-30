/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http;

import java.io.Reader;
import java.nio.ByteBuffer;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface HttpRequestI extends HttpMessageI {
	public String getUri();

	public ByteBuffer getContent();
	
	public Reader getContentAsReader();

}
