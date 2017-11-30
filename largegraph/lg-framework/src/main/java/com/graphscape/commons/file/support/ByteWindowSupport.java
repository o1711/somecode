/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.graphscape.commons.debug.support.TracableSupport;
import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.ResolverI;
import com.graphscape.commons.record.SerializerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class ByteWindowSupport extends TracableSupport implements ByteWindowI {

	protected List<ByteWindowI> childList = new ArrayList<ByteWindowI>();

	protected Map<String, ByteWindowI> childMap = new HashMap<String, ByteWindowI>();
	protected ResolverI<ByteCursorI, Long> widthResolver;

	protected ResolverI<ByteCursorI, Long> repeatResolver;

	protected ByteWindowI parent;

	protected ByteWindowI left;

	protected SerializerI serializer;

	protected String name;

	/**
	 * @param name
	 */
	public ByteWindowSupport(String name) {
		this.name = name;
	}

	@Override
	public ByteWindowI addChild(ByteWindowI child) {

		ByteWindowI old = this.childMap.get(child.getName());
		if (old != null) {
			throw new GsException("duplicated child:" + child);
		}
		child.setParent(this);//
		this.childList.add(child);
		this.childMap.put(child.getName(), child);
		this.applyTracer(child);
		return this;
	}

	@Override
	public void setParent(ByteWindowI parent) {
		if (this.getParent() != null) {
			throw new GsException("already has parent,parent:" + this.parent);
		}
		this.parent = parent;
		this.left = this.parent == null ? null : this.parent.getLastChild();

	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<ByteWindowI> getChildList() {

		return this.childList;
	}

	@Override
	public ByteWindowI getParent() {

		return this.parent;
	}

	@Override
	public ByteWindowI getRoot() {
		ByteWindowI rt = this;
		while (rt.getParent() != null) {
			rt = rt.getParent();
		}
		return rt;
	}

	@Override
	public long getWidth(ByteCursorI bf) {

		long rt = this.widthResolver.resolve(bf);
		if (rt < 0) {
			throw new GsException("negetive width:" + rt + " resovled,window:" + this + ",cursor:"
					+ bf.getPointer() + ",resover:" + this.widthResolver);
		}
		if (rt > 84849747842133870L - 1) {
			throw new GsException("too large width:" + rt + " resovled,window:" + this + ",cursor:"
					+ bf.getPointer() + ",resover:" + this.widthResolver);
		}
		return rt;
	}

	@Override
	public long getOccurs(ByteCursorI bf) {
		long rt = this.repeatResolver.resolve(bf);
		if (rt <= 0) {
			throw new GsException("zero/negetive occurs,window:" + this.name + ",cursor:" + bf.getPointer()
					+ ",resolver:" + this.repeatResolver);
		}
		return rt;

	}

	@Override
	public boolean isRoot() {
		return this.parent == null;
	}

	@Override
	public ByteWindowI getLeft() {
		return this.left;
	}

	@Override
	public ByteWindowI getRight() {
		if (this.parent == null) {
			return null;
		}
		List<ByteWindowI> cL = this.parent.getChildList();
		ByteWindowI rt = null;
		for (int i = 0; i < cL.size(); i++) {
			ByteWindowI c = cL.get(i);
			if (c.getName().equals(this.name)) {//
				if (i + 1 < cL.size()) {
					rt = cL.get(i + 1);
				}
				break;
			}

		}
		return rt;
	}

	@Override
	public ByteWindowI getChild(String windowName, boolean b) {
		ByteWindowI rt = this.childMap.get(windowName);
		if (b && rt == null) {
			throw new GsException("no child:" + windowName);
		}
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.file.ByteWindowI#serializer(com.graphscape.commons
	 * .record.SerializableI)
	 */
	@Override
	public <T> ByteWindowI serializer(SerializerI<T> ser) {
		this.serializer = ser;
		return this;

	}

	@Override
	public <T> SerializerI<T> getSerializer() {
		return this.serializer;
	}

}
