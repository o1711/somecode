package com.graphscape.largegraph.console.tst;

import com.graphscape.commons.lang.ErrorInfos;

public class TstScenarioResult {

	private ErrorInfos errorInfos = new ErrorInfos();

	/**
	 * @return the errorInfos
	 */
	public ErrorInfos getErrorInfos() {
		return errorInfos;
	}

	public void assertNoError() {
		this.errorInfos.assertNoError();
	}

	public boolean hasError() {
		return this.errorInfos.hasError();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (this.errorInfos.hasError()) {
			sb.append(this.errorInfos.toString());
		} else {
			sb.append("scenario success!");
		}

		return sb.toString();
	}

}
