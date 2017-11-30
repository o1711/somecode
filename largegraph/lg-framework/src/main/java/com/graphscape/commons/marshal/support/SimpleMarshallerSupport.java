/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.support;

import com.graphscape.commons.marshal.MarshallingProviderI;
import com.graphscape.commons.marshal.MarshallerI;

/**
 * @author wu
 * 
 */
public abstract class SimpleMarshallerSupport<T, SER> implements MarshallerI<SER> {
	protected MarshallingProviderI<SER> provider;

	protected String typeCode;

	protected Class<?> dataClass;

	public SimpleMarshallerSupport(String tc, Class<?> dc, MarshallingProviderI<SER> f) {
		this.provider = f;
		this.typeCode = tc;
		this.dataClass = dc;
	}

	/* */
	@Override
	public T unmarshal(SER s) {
		SER ser = (SER) s;
		try{
			return this.decodeInternal(ser);			
		}catch(ClassCastException e){
			throw e;
		}

	}

	/*
	
	 */
	@Override
	public SER marshal(Object ud) {

		return this.encodeInternal((T) ud);
	}

	protected abstract T decodeInternal(SER js);

	protected abstract SER encodeInternal(T d);

	public String getTypeCode() {
		return typeCode;
	}

	public Class<?> getDataClass() {
		return dataClass;
	}

}
