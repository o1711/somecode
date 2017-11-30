package org.ta.common.config;

/**
 * Nov 4, 2011
 */

/**
 * @author wuzhen
 * 
 */
public class TaException extends RuntimeException {

	public TaException() {
		this((String) null);
	}

	public TaException(Throwable e) {
		this(null, e);

	}

	public TaException(String msg) {
		this(msg, (Throwable) null);
	}

	public TaException(String msg, Throwable t) {
		super(msg, t);
	}

	public TaException(TaErrors es) {
		this("", es);
	}

	public TaException(String msg, TaErrors es) {
		this(msg + es.getAsText());
	}

	/**
	 * @param e
	 * @return
	 */
	public static RuntimeException toRuntimeException(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		}
		return new TaException(t);

	}
}
