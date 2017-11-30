/**
 * Jun 23, 2012
 */
package com.graphscape.commons.marshal.support;

import com.graphscape.commons.marshal.MarshallerI;
import com.graphscape.commons.marshal.MarshallingProviderI;

/**
 * @author wu
 * 
 */
public abstract class MarshallingProviderSupport<T> implements MarshallingProviderI<T> {

	public MarshallingProviderSupport() {

	}

	/* */
	@Override
	public MarshallerI<T> getMarshaller(Class<?> dataCls) {
		return this.getMarshaller(dataCls, false);
	}

}
