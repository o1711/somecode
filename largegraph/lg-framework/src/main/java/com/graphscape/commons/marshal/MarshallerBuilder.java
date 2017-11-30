/**
 * Dec 23, 2013
 */
package com.graphscape.commons.marshal;

import com.graphscape.commons.lang.Builder;
import com.graphscape.commons.marshal.provider.default_.SimpleStringMarshallingProvider;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class MarshallerBuilder<T> extends Builder<MarshallerI<T>> {

	private MarshallingProviderI provider;

	private Class cls;

	public MarshallerBuilder<T> jsonStringProvider() {
		this.provider = new SimpleStringMarshallingProvider();
		return this;
	}

	public MarshallerBuilder<T> type(Class cls) {
		this.cls = cls;
		return this;
	}

	@Override
	public MarshallerI<T> build() {
		if (cls == null) {
			throw new IllegalArgumentException("cls not pointed out.");
		}
		return this.provider.getMarshaller(cls);
	}

}
