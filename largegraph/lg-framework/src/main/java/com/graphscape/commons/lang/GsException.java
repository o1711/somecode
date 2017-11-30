package com.graphscape.commons.lang;

/**
 * 
 * @author wu
 * 
 */
public class GsException extends RuntimeException {

	public GsException() {
		this((String) null);//
	}

	public GsException(String msg) {
		super(msg);
	}

	public GsException(Throwable cause) {
		super(cause);
	}

	public GsException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public GsException(ErrorInfos errorInfos) {
		super(errorInfos.toString());
	}

	/**
	 * @param e
	 * @return
	 */
	public static RuntimeException toRuntimeException(Throwable t) {
		if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		}
		throw new GsException(t);
	}

	public GsException todo() {
		return new GsException("todo");
	}

}
