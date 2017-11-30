/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.util.ClassWrapper;

/**
 * @author wu
 * 
 */
public class SimpleMarshallingProviderSupport<T> extends MarshallingProviderSupport<T> {

	private Map<String, MarshallerI<T>> jcMap = new HashMap<String, MarshallerI<T>>();

	private Map<Class, String> classMap = new HashMap<Class, String>();

	public SimpleMarshallingProviderSupport() {

	}

	protected void add(SimpleMarshallerSupport cd) {
		Class cls = cd.getDataClass();
		String tc = cd.getTypeCode();
		String oldx = this.classMap.put(cls, tc);
		if (oldx != null) {
			throw new GsException("duplicated:" + cls + ",oldx:" + oldx);
		}
		MarshallerI old = this.jcMap.put(tc, cd);
		if (old != null) {
			throw new GsException("duplicated:" + tc);
		}

	}

	@Override
	public MarshallerI<T> getMarshaller(Class<?> dataCls, boolean force) {
		String tc = this.getTypeCode(dataCls, true);
		MarshallerI<T> rt = this.getMarshaller(tc);
		if (rt == null && force) {
			throw new GsException("no codec for type:" + dataCls);
		}
		return rt;

	}

	protected String getTypeCode(Class<?> dataCls, boolean force) {

		String tc = this.classMap.get(dataCls);
		if (tc != null) {
			return tc;
		}

		List<Class> superL = new ClassWrapper(dataCls).getSuperTypeList();//
		List<String> codeL = new ArrayList<String>();
		for (Class sc : superL) {
			String stc = this.classMap.get(sc);
			if (stc != null) {
				codeL.add(stc);
			}
		}

		if (codeL.isEmpty()) {
			if (force) {
				throw new GsException("no type code for:" + dataCls);
			}
			return null;
		} else if (codeL.size() == 1) {
			return codeL.get(0);
		} else {
			throw new GsException("too much type code:" + codeL + ", for class:" + dataCls);
		}

	}

	/* */
	@Override
	public MarshallerI<T> getMarshaller(String type) {

		MarshallerI<T> rt = this.jcMap.get(type);
		if (rt == null) {
			throw new GsException("no codec found for type code:" + type + ",all:" + this.jcMap.keySet());
		}
		return rt;
	}

}
