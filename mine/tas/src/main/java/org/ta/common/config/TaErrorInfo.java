package org.ta.common.config;
/**
 * 2011-3-24
 */


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The place for any error information to be transfered.
 * 
 * @see ErrorInfs
 * @author Wu Zhen
 * 
 */
public class TaErrorInfo implements java.io.Serializable {
	private String code;

	private String message;

	private Throwable error;

	/**
	 * Constructor
	 * 
	 * @param code
	 */
	public TaErrorInfo(String code) {
		this(code, null, null);
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 */
	public TaErrorInfo(String code, String msg) {
		this(code, msg, null);
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 */
	public TaErrorInfo(Throwable t) {
		this(null, null, t);
	}

	/**
	 * Constructor
	 * 
	 * @param code
	 */
	public TaErrorInfo(String code, String msg, Throwable t) {
		this.code = code == null ? "unknown" : code;
		this.message = msg;
		this.error = t;
	}

	/**
	 * For debuging.
	 * 
	 * @return
	 */
	public String getAsText() {
		StringBuffer rt = new StringBuffer();
		rt.append("" + this.code + ",");
		if (message != null) {
			rt.append(message);
			rt.append("\n");
		}
		if (this.error != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bos);
			this.error.printStackTrace(ps);
			rt.append(bos.toString());
		}
		return rt.toString();
	}

	/**
	 * Utility method of constructing.
	 * 
	 * @param code
	 * @param msg
	 * @return
	 */
	public static TaErrorInfo toError(String code, String msg) {
		return new TaErrorInfo(code, msg);
	}

	/**
	 * Utility for constructing.
	 * 
	 * @param t
	 * @return
	 */
	public static TaErrorInfo toError(Throwable t) {
		return new TaErrorInfo(t);
	}

	public static String toString(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		
		return sw.toString();
	}

}
