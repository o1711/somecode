/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http.provider.netty;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.jboss.netty.handler.codec.http.HttpRequest;

import com.graphscape.commons.http.HttpRequestI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.util.ByteBufferInputStream;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class NettyHttpRequest extends NettyHttpMessage<HttpRequest> implements
		HttpRequestI {

	public NettyHttpRequest(HttpRequest req) {
		super(req);
	}

	/**
	 * @return
	 */
	public String getUri() {

		return this.target.getUri();
	}

	@Override
	public ByteBuffer getContent() {
		return this.target.getContent().toByteBuffer();

	}

	@Override
	public Reader getContentAsReader() {
		String ccode = this.getCharset();
		try {
			return new InputStreamReader(new ByteBufferInputStream(
					this.getContent()), ccode);
		} catch (UnsupportedEncodingException e) {
			throw new GsException(e);
		}
	}

}
