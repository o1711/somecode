/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.LeafByteFormaterI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.provider.resolver.BrotherFieldValueAsLongResolver;
import com.graphscape.commons.file.provider.resolver.ChildTotalWidthResolver;
import com.graphscape.commons.file.provider.resolver.FixLongValueResolver;
import com.graphscape.commons.file.support.ByteWindowSupport;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.ResolverI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultByteWindow extends ByteWindowSupport {

	protected LeafByteFormaterI formater;

	public DefaultByteWindow(String name) {
		this(name, new ChildTotalWidthResolver());//

	}

	/**
	 * @param name
	 */
	public DefaultByteWindow(String name, long width) {
		this(name, new FixLongValueResolver(width));
	}

	public DefaultByteWindow(String name, ResolverI<ByteCursorI, Long> widthResolver) {

		this(name, widthResolver, 1);//
	}

	public DefaultByteWindow(String name, ResolverI<ByteCursorI, Long> widthResolver, String repeatBrother) {
		this(name, widthResolver, new BrotherFieldValueAsLongResolver(repeatBrother));
	}

	public DefaultByteWindow(String name, long width, long repeat) {
		this(name, new FixLongValueResolver(width), repeat);
	}

	public DefaultByteWindow(String name, ResolverI<ByteCursorI, Long> widthResolver, long repeat) {

		this(name, widthResolver, new FixLongValueResolver(repeat));//
	}

	public DefaultByteWindow(String name, long width, String repeatBrother) {
		this(name, width, new BrotherFieldValueAsLongResolver(repeatBrother));
	}

	public DefaultByteWindow(String name, String widthBrother) {
		this(name, new BrotherFieldValueAsLongResolver(widthBrother), 1);
	}

	public DefaultByteWindow(String name, String widthBrother, String repeatBrother) {
		this(name, new BrotherFieldValueAsLongResolver(widthBrother), new BrotherFieldValueAsLongResolver(
				repeatBrother));
	}

	public DefaultByteWindow(String name, long width, ResolverI<ByteCursorI, Long> repeatResolver) {
		this(name, new FixLongValueResolver(width), repeatResolver);
	}

	public DefaultByteWindow(String name, ResolverI<ByteCursorI, Long> widthResolver,
			ResolverI<ByteCursorI, Long> repeatResolver) {

		super(name);
		this.widthResolver = widthResolver;
		this.repeatResolver = repeatResolver;
		this.applyTracer(this.widthResolver);
		this.applyTracer(this.repeatResolver);
	}

	@Override
	public ByteCursorI open(PlainRegionI region) {
		return open(region, 0);
	}

	@Override
	public ByteCursorI open(PlainRegionI region, long offset) {
		ByteCursorI rt = new DefaultByteCursor(this, region, offset);
		this.applyTracer(rt);//
		return rt;

	}

	@Override
	public ByteCursorI open(byte[] content, int offset) {

		return open(MemoryPlainRegion.valueOf(content), offset);

	}

	@Override
	public ByteCursorI open(byte[] content) {
		return this.open(content, 0);
	}

	@Override
	public ByteWindowI getFirstChild() {
		if (this.childList.isEmpty()) {
			return null;
		}
		return this.childList.get(0);

	}

	@Override
	public ByteWindowI getLastChild() {
		if (this.childList.isEmpty()) {
			return null;
		}
		return this.childList.get(this.childList.size() - 1);
	}

	@Override
	public boolean isFirstChild() {
		if (this.parent == null) {
			return false;
		}
		if (this.parent.getFirstChild() == this) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isLastChild() {
		if (this.parent == null) {
			return false;
		}

		if (this.parent.getLastChild() == this) {
			return true;
		}

		return false;
	}

	@Override
	public ByteCursorI open() {
		PlainRegionI reg = new MemoryPlainRegion();
		return open(reg);
	}

	@Override
	public ErrorInfos validate(PlainRegionI region, long offset) {
		ErrorInfos eis = new ErrorInfos();
		new WindowValidator(this, region, offset).execute(eis);
		return eis;

	}

	@Override
	public LeafByteFormaterI getFormater() {

		return this.formater;
	}

	@Override
	public ByteWindowI formater(LeafByteFormaterI formater) {
		this.formater = formater;
		return this;
	}

}
