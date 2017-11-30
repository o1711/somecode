/**
 * Jan 14, 2014
 */
package com.graphscape.commons.file.support;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.RootByteFormaterI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.PropertiesI;
import com.graphscape.commons.lang.provider.default_.DefaultProperties;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public abstract class RootByteFormaterSupport extends ByteFormaterSupport implements RootByteFormaterI {

	@Override
	protected void formatInternal(ByteCursorI cur, Writer writer) throws IOException {
		this.format(false, cur, writer);
	}

	@Override
	public void format(ByteWindowI window, PlainRegionI region, Writer writer) {
		this.format(false, window, region, writer);
	}

	@Override
	public void format(ByteWindowI window, PlainRegionI region, PrintStream ps) {
		this.format(false, window, region, ps);
	}

	@Override
	public void format(boolean next, ByteWindowI window, PlainRegionI region, PrintStream ps) {
		Writer writer = new OutputStreamWriter(ps);
		ByteCursorI cursor = window.open(region, 0);
		this.format(next, cursor, writer);
		try {
			writer.flush();
		} catch (IOException e) {
			this.onException(e);
		}

	}

	@Override
	public void format(boolean next, ByteWindowI window, PlainRegionI region, Writer writer) {
		ByteCursorI cursor = window.open(region, 0);
		this.format(next, cursor, writer);

	}

	@Override
	public void format(boolean next, ByteCursorI cursor, PrintStream ps) {

		this.format(cursor, new OutputStreamWriter(ps));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.file.ByteFormaterI#format(com.graphscape.commons
	 * .file.ByteCursorI, java.io.Writer)
	 */
	@Override
	public void format(boolean next, ByteCursorI cursor, Writer writer) {
		PropertiesI<Object> context = new DefaultProperties<Object>();
		cursor.root();
		try {
			int idx = 0;
			while (true) {
				this.beforeRootFormat(idx, context, cursor, writer);

				try {
					this.formatRoot(context, cursor, writer);

				} finally {
					this.finishRootFormat(context, cursor, writer);
				}
				writer.flush();
				cursor.root();//
				if (next) {
					if (!cursor.hasNext()) {
						break;
					}
					cursor.next();//
				} else {
					break;
				}
				idx++;
			}
			this.afterAllFormated(idx, context, cursor, writer);
			writer.flush();
		} catch (Exception e) {
			this.onException(e);
		}

	}

	protected void onException(Exception e) {
		throw GsException.toRuntimeException(e);
	}

	protected abstract void afterAllFormated(int total, PropertiesI<Object> context, ByteCursorI cur,
			Writer writer) throws IOException;

	protected abstract void beforeRootFormat(int idx, PropertiesI<Object> context, ByteCursorI cur,
			Writer writer) throws IOException;

	protected abstract void finishRootFormat(PropertiesI<Object> context, ByteCursorI cur, Writer writer)
			throws IOException;

	protected abstract void doFormat(PropertiesI<Object> context, ByteCursorI cur, byte[] content,
			Writer writer) throws IOException;

	protected void formatRoot(PropertiesI<Object> context, ByteCursorI cur, Writer writer) throws IOException {
		// find the left most leaf node.
		while (!cur.getPointer().getWindow().getChildList().isEmpty()) {// have
																		// child
			if (null == cur.firstChild()) {
				throw new GsException("no first child,bug?");
			}
		}

		byte[] content = cur.read();//

		this.doFormat(context, cur, content, writer);

		this.formatRight(context, cur, writer);

	}

	public void formatRight(PropertiesI<Object> context, ByteCursorI cur, Writer writer) throws IOException {
		if (cur.right() != null) {// have right,so format it.
			this.formatRoot(context, cur, writer);
			return;//
		}

		// now in the last child,so back to uncle:the parent's right
		// back to parent's right
		if (null == cur.parent()) {
			// no parent.
			return;// over
		}
		formatRight(context, cur, writer);//
	}

}
