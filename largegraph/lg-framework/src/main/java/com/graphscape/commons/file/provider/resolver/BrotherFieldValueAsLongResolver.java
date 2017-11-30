/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider.resolver;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.support.CursorBasedResolver;
import com.graphscape.commons.lang.CallbackI;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.ResolverI;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class BrotherFieldValueAsLongResolver extends CursorBasedResolver<Long> implements
		ResolverI<ByteCursorI, Long> {

	protected String brotherWindowName;

	protected boolean allowZeroWidth = true;

	public BrotherFieldValueAsLongResolver(String windowName) {
		this.brotherWindowName = windowName;
	}

	@Override
	public Long resolve(ByteCursorI s) {
		this.beforeExecute(s.getPointer());//
		try {
			return this.resolveInternal(s);
		} finally {
			this.afterExecute();

		}

	}

	public Long resolveInternal(final ByteCursorI s) {
		final long aoff = s.getAbsoluteOffset();

		return s.pushAndPop(new CallbackI<ByteCursorI, Long>() {

			@Override
			public Long execute(ByteCursorI t) {

				String windowName = t.getPointer().getWindow().getName();

				String brotherName = BrotherFieldValueAsLongResolver.this.brotherWindowName;

				if (windowName.equals(brotherName)) {
					throw new GsException("bug? the brother window's name is same with the current one:"
							+ brotherName);
				}

				t.left(brotherName, true);

				byte[] value = t.read(0, (int) t.getLength());

				Number rtN = ByteArrayUtil.readNumber(value).longValue();

				long rt = rtN.longValue();
				if (rt < 0) {
					throw new GsException("the value in the window:" + t.getPointer() + " is negative:" + rt
							+ ", but it indicate the occurs of another window:" + windowName + ",offset:"
							+ t.getAbsoluteOffset() + ",the source offset:" + aoff);
				}
				if (rt == 0 && !BrotherFieldValueAsLongResolver.this.allowZeroWidth) {

					throw new GsException("the value in the window:" + t.getPointer()
							+ " is zero, but it indicate the occurs of another window:" + windowName
							+ ",offset:" + t.getAbsoluteOffset() + ",the source offset:" + aoff);

				}
				// if (rt > 10000000) {
				// byte[] value2 = t.read(0, (int) t.getLength());
				//
				// throw new GsException("overflow? value:" +
				// ByteArrayUtil.hex(value));
				// }
				return rt;

			}
		});

	}
}
