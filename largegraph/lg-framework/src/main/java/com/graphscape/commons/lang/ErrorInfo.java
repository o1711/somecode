/**
 */
package com.graphscape.commons.lang;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.graphscape.commons.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class ErrorInfo {

	private String id;
	
	private String source;

	private String message;

	private List<String> detail;

	public ErrorInfo(String source) {
		this(source, (String) null);
	}

	public ErrorInfo(String source, String msg) {
		this(source, msg, null);
	}

	public ErrorInfo(Throwable t) {
		this(null, t);
	}

	public ErrorInfo(String source, Throwable t) {
		this(source, null, t);
	}
	public ErrorInfo(String source, String msg, Throwable t) {
		this(source,msg,t,UUID.randomUUID().toString());
	}
	public ErrorInfo(String source, String msg, Throwable t, String id) {
		this.id = id;
		this.source = source;
		this.message = msg;
		this.detail = new ArrayList<String>();
		
		if (t != null) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(os);
			t.printStackTrace(ps);
			String[] ss = os.toString().split("\n");
			this.detail.addAll(Arrays.asList(ss));
		}
	}

	public String getId(){
		return this.id;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the detail
	 */
	public List<String> getDetail() {
		return detail;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ErrorInfo)) {
			return false;
		}
		ErrorInfo ei = (ErrorInfo) o;
		return ObjectUtil.nullSafeEquals(ei.source, this.source)
				&& ObjectUtil.nullSafeEquals(ei.message, this.message)
				&& ObjectUtil.nullSafeEquals(ei.detail, ei.detail);
	}

	/* */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(this.message);
		sb.append(",source:");
		sb.append(this.source);
		sb.append(",detail:");
		sb.append("");
		for (String line : this.detail) {
			sb.append(line);
			sb.append("\n");

		}
		return sb.toString();

	}

	/**
	 * @return the source
	 */
	public String getCode() {
		return source;
	}

	/**
	 * @param t
	 * @return
	 */
	public static ErrorInfo valueOf(Throwable t) {
		
		return new ErrorInfo(t);
	}

}
