/**
 */
package com.graphscape.commons.lang;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.util.ObjectUtil;
import com.graphscape.commons.util.StringUtil;

/**
 * @author wu
 * 
 */
public class ErrorInfos {
	private List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();

	public List<ErrorInfo> getErrorInfoList() {
		return this.errorInfoList;
	}

	public boolean hasError() {
		return !this.errorInfoList.isEmpty();
	}
	
	public String getErrorCodeListAsString(char sep){
		List<String> rt = this.getErrorCodeList();
		
		return StringUtil.toString(rt, sep);
		
	}

	public List<String> getErrorCodeList() {
		List<String> rt = new ArrayList<String>();
		for (ErrorInfo ei : this.errorInfoList) {
			rt.add(ei.getCode());
		}
		return rt;
	}

	public ErrorInfos addAll(ErrorInfos eis) {
		for (ErrorInfo ei : eis.getErrorInfoList()) {
			this.add(ei);
		}
		return this;
	}

	public ErrorInfos add(Throwable t) {
		return this.add(ErrorInfo.valueOf(t));
	}

	public ErrorInfos add(String msg) {
		return this.add(new ErrorInfo(msg));
	}

	public ErrorInfos add(ErrorInfo ei) {
		this.errorInfoList.add(ei);
		return this;
	}

	/* */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ErrorInfos)) {
			return false;
		}
		return ObjectUtil.nullSafeEquals(this.errorInfoList, ((ErrorInfos) obj).errorInfoList);
	}

	public GsException toException() {
		return new GsException(this);
	}

	/* */
	@Override
	public String toString() {
		return this.errorInfoList.toString();

	}

	/**
	 * 
	 */
	public void assertNoError() {
		if (this.hasError()) {
			throw this.toException();
		}
	}

}
