/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.support;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.BytePosition;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class ByteCursorSupport extends ProxyReaderWriter implements ByteCursorI {

	private static final Logger LOG = LoggerFactory.getLogger(ByteCursorSupport.class);

	protected PlainRegionI region;

	protected long rootAbsoluteOffset;// when root move to right, change this
										// value.

	protected Stack<DefaultByteInstance> userStack = new Stack<DefaultByteInstance>();

	protected DefaultByteInstance instance;

	public ByteCursorSupport(ByteWindowI window, PlainRegionI region) {
		this(window, region, 0);
	}

	public ByteCursorSupport(ByteWindowI window, PlainRegionI region, long offset) {
		super(null, null);

		if (!window.isRoot()) {
			throw new GsException("cursor from the none-root window is not supported");
		}
		this.instance = DefaultByteInstance.valueOf(window, 0);
		this.region = region;
		this.rootAbsoluteOffset = offset;
	}

	protected int id() {
		return this.instance.getId();
	}

	protected ByteWindowI window() {
		return this.instance.getWindow();
	}

	@Override
	public ByteCursorI right() {

		long should = this.window().getOccurs(this);
		if (should < 1) {
			throw new GsException("should not occur for window:" + this.window().getName());
		}
		if (this.id() + 1 < should) {
			// moving position by increase the instance id,since the window not
			// changed.
			this.instance.nextId();
		} else if (this.id() + 1 == should) {//
			ByteWindowI rw = this.window().getRight();
			if (rw == null) {// no more right
				return null;
			}
			this.instance.set(rw, 0);
		} else {
			throw new GsException("repeat overflow,bug");
		}

		return this;
	}

	public boolean isLeaf() {
		return this.window().getChildList().isEmpty();
	}

	@Override
	public long getAbsoluteOffset() {
		this.beforeExecute("getAbsoluteOffset", this.instance);
		try {
			return this.doGetAbsoluteOffset();
		} finally {
			this.afterExecute();
		}

	}

	protected long doGetAbsoluteOffset() {
		if (LOG.isDebugEnabled()) {
			// LOG.debug("getAbslureOffset,instance:" + this.instance + ",loop:"
			// + loop);
		}
		if (this.isRoot()) {// root.
			LOG.debug("got it for isRoot,rootAbsoluteOffset:" + this.rootAbsoluteOffset);
			return this.rootAbsoluteOffset;
		}

		if (this.isFirstChild()) {

			long rt = this.pushAndPop(new CallbackI<ByteCursorI, Long>() {

				@Override
				public Long execute(ByteCursorI t) {

					return ((ByteCursorSupport) t.parent()).getAbsoluteOffset();
				}
			});
			LOG.debug("got it for parent's offset:" + rt);
			return rt;
		} else {
			long leftOffset = this.pushAndPop(new CallbackI<ByteCursorI, Long>() {

				@Override
				public Long execute(ByteCursorI t) {

					return ((ByteCursorSupport) t.left()).getAbsoluteOffset();
				}
			});
			long leftLength = this.pushAndPop(new CallbackI<ByteCursorI, Long>() {

				@Override
				public Long execute(ByteCursorI t) {

					return ((ByteCursorSupport) t.left()).getLength();
				}
			});
			LOG.debug("got it for leftOffset:" + leftOffset + "+leftLength:" + leftLength);
			return leftOffset + leftLength;

		}

	}

	@Override
	public ByteCursorI parent() {
		// LOG.debug("parent()");
		if (this.instance.moveToParent()) {
			return this;
		}

		return null;
	}

	public boolean isRoot() {
		return this.instance.isRoot();
	}

	@Override
	public ByteCursorI root() {
		ByteCursorI rt = this;
		while (rt != null) {//
			rt = rt.parent();
		}
		return this;
	}

	@Override
	public ByteCursorI left() {
		if (this.instance.moveToLeft(this)) {
			return this;
		}
		return null;
	}

	/**
	 * Note,this is jump,empty the left stack,and push parent stack
	 */
	@Override
	public ByteCursorI firstChild() {
		if (this.instance.moveToFirstChild()) {
			return this;
		}
		return null;
	}

	@Override
	public ByteCursorI firstBrother() {
		ByteCursorI rt = this;
		while (rt.left() != null) {
			rt = rt.left();
		}
		return rt;
	}

	@Override
	public BytePosition getPointer() {
		return this.instance;
	}

	@Override
	public ByteCursorI left(String name, boolean force) {

		String theName = this.window().getName();
		ByteCursorI left = this.left();

		while (left != null) {
			if (left.getPointer().getWindow().getName().equals(name)) {
				break;
			}
			left = left.left();
		}
		if (left == null && force) {

			throw new GsException("from:" + theName + " cannot not found left with name:" + name
					+ ",now the cursor point to the first brother.");
		}
		return this;
	}

	@Override
	public boolean isFirstChild() {
		return this.instance.isFirstChild();
	}

	@Override
	public boolean isLastChild() {
		if (!this.instance.getWindow().isLastChild()) {
			return false;
		}

		return this.instance.getId() + 1 == this.instance.getWindow().getOccurs(this);

	}

	@Override
	public boolean isWindow(ByteWindowI window) {
		return this.window() == window;
	}

	@Override
	public long getLength() {
		this.beforeExecute("getLength:" + this.instance.toString());
		try {
			return this.window().getWidth(this);
		} finally {
			this.afterExecute();
		}

	}

	@Override
	public ByteCursorI push() {// save the current position.
		DefaultByteInstance position = this.instance.clone();

		this.userStack.push(position);//
		return this;
	}

	@Override
	public ByteCursorI pop() {
		this.instance = this.userStack.pop();
		return this;
	}

	@Override
	public <T> T pushAndPop(CallbackI<ByteCursorI, T> call) {
		ByteCursorI top = this.push();
		try {
			return call.execute(top);
		} finally {
			this.pop();
		}

	}

	@Override
	public ByteCursorI next() {
		if(!this.hasNext()){
			return null;
		}
		long len = this.getLength();
		this.rootAbsoluteOffset += len;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.ByteCursorI#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (!this.isRoot()) {
			return false;
		}
		long aoffset = this.getAbsoluteOffset();
		long len = this.getLength();
		long rlen = this.region.getLength();
		return aoffset + len < rlen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.file.ByteCursorI#read()
	 */
	@Override
	public byte[] read() {
		long off = this.getAbsoluteOffset();
		long len = this.getLength();
		return this.region.read(off, (int) len);
	}

	@Override
	public ByteCursorI right(int step) {
		for (int i = 0; i < step; i++) {
			ByteCursorI rt = right();
			if (rt == null) {
				return null;
			}
		}
		return this;
	}

	@Override
	public ByteCursorI lastChild() {

		ByteCursorI rt = this.firstChild();

		if (rt == null) {// no child
			return null;
		}

		while (rt != null) {
			rt = rt.right();
		}
		return this;
	}

	@Override
	public ByteCursorI firstLeaf() {
		throw new GsException("TODO");
	}

	@Override
	public PlainRegionI getRegion() {
		return this.region;
	}
}
