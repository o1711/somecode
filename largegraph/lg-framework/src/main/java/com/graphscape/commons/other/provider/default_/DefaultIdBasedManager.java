/**
 * Dec 15, 2013
 */
package com.graphscape.commons.other.provider.default_;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.graphscape.commons.lang.BuilderI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.WithIdI;
import com.graphscape.commons.lang.support.DefaultWithId;
import com.graphscape.commons.other.IdBasedManagerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultIdBasedManager<T> implements IdBasedManagerI<T> {

	private Map<String, T> map = new HashMap<String, T>();

	private BuilderI<T> builder;

	public DefaultIdBasedManager(BuilderI<T> builder) {
		this.builder = builder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.other.IdBasedManagerI#getMember(java.lang.Object)
	 */
	@Override
	public T getMember(String key) {
		return map.get(key);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.other.IdBasedManagerI#getMember(java.lang.Object,
	 * boolean)
	 */
	@Override
	public T getMember(String id, boolean force) {
		T rt = map.get(id);
		if (rt == null && force) {
			throw new GsException("not found member with id:" + id);
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.other.IdBasedManagerI#addMember(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void addMember(String id, T value) {
		this.map.put(id, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.other.IdBasedManagerI#createMember()
	 */
	@Override
	public WithIdI<T> createMember() {
		T rt = this.builder.build();
		String id = UUID.randomUUID().toString();
		this.addMember(id, rt);

		return new DefaultWithId<T>(id, rt);

	}

}
