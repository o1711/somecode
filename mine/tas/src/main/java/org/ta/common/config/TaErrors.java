package org.ta.common.config;
/**
 * Nov 7, 2011
 */


import java.util.ArrayList;
import java.util.List;

/**
 * @author wuzhen
 * 
 */
public class TaErrors implements java.io.Serializable {
	private List<TaErrorInfo> errorList = new ArrayList<TaErrorInfo>();

	/**
	 * Empty means no error information.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return this.errorList.isEmpty();
	}

	/**
	 * Merge from another informations.
	 * 
	 * @param eis
	 */
	public void addAll(TaErrors eis) {
		this.errorList.addAll(eis.errorList);
	}

	/**
	 * Retrieve all information.
	 * 
	 * @return
	 */
	public List<TaErrorInfo> getErrorList() {
		return errorList;
	}

	/**
	 * Add a error information by message string.
	 * 
	 * @param msg
	 */
	public void addError(String msg) {
		this.addError(null, msg);
	}

	/**
	 * Add a error information by error code and detail message string.
	 * 
	 * @param code
	 * @param msg
	 */
	public void addError(String code, String msg) {

		this.addError(TaErrorInfo.toError(code, msg));
	}

	/**
	 * Add a error information.
	 * 
	 * @param ei
	 */
	public void addError(TaErrorInfo ei) {
		this.errorList.add(ei);
	}

	/**
	 * @param t
	 */
	public void addError(Throwable t) {
		TaErrorInfo ce = TaErrorInfo.toError(t);
		this.errorList.add(ce);
	}

	/**
	 * For logging the information.
	 * 
	 * @return
	 */
	public String getAsText() {
		StringBuffer rt = new StringBuffer();
		for (TaErrorInfo ei : this.errorList) {

			rt.append("" + ei.getAsText());
		}
		return rt.toString();
	}

	public int size() {
		return this.errorList.size();
	}
}
