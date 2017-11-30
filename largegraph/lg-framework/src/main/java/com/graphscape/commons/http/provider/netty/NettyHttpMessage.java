/**
 * Dec 14, 2013
 */
package com.graphscape.commons.http.provider.netty;

import javax.ws.rs.core.HttpHeaders;

import org.jboss.netty.handler.codec.http.HttpMessage;

import com.graphscape.commons.http.HttpMessageI;
import com.graphscape.commons.lang.Wrapper;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class NettyHttpMessage<T extends HttpMessage> extends Wrapper<T>
		implements HttpMessageI {

	/**
	 * @param t
	 */
	public NettyHttpMessage(T t) {
		super(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.http.HttpMessageI#setHeader(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void setHeader(String key, String value) {
		this.target.setHeader(key, value);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.http.HttpMessageI#getHeader(java.lang.String)
	 */
	@Override
	public String getHeader(String key) {

		return this.target.getHeader(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.http.HttpMessageI#getCharacterEncoding()
	 */
	@Override
	public String getCharset() {
		String ctypeS = this.getContentType();
		ContentTypeHeaderValue ctype = ContentTypeHeaderValue.parse(ctypeS);
		if (ctype == null) {
			return null;
		}
		return ctype.getCharset();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.http.HttpMessageI#getContentType()
	 */
	@Override
	public String getContentType() {
		return this.getHeader(HttpHeaders.CONTENT_TYPE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.http.HttpMessageI#getContentLength()
	 */
	@Override
	public long getContentLength() {
		// TODO Auto-generated method stub
		return this.target.getContentLength();
	}

	@Override
	public void setContentType(String string) {
		this.target.setHeader(HttpHeaders.CONTENT_TYPE, string);

	}

}
