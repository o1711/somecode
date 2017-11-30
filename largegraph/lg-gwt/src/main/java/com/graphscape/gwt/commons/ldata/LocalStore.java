/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 9, 2012
 */
package com.graphscape.gwt.commons.ldata;

import com.graphscape.gwt.commons.ldata.Html5LocalStore;
import com.graphscape.gwt.commons.ldata.LocalData;
import com.graphscape.gwt.commons.ldata.LocalStore;
import com.graphscape.gwt.commons.ldata.MemoryLocalStore;

/**
 * @author wu
 * 
 */
public abstract class LocalStore {

	private static LocalStore ME;

	public static LocalStore getInstance() {
		if (ME != null) {
			return ME;
		}
		Html5LocalStore h5 = Html5LocalStore.getInstance(false);
		if (h5 == null) {
			ME = MemoryLocalStore.ME;
		} else {
			ME = h5;
		}
		return ME;
	}

	protected LocalStore() {
	}

	protected abstract String getValue(String key);

	protected abstract void setValue(String key, String value);

	public LocalData getData(String key) {
		LocalData rt = new LocalData(key, this);
		return rt;
	}

}
