/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider.resolver;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.support.CursorBasedResolver;
import com.graphscape.commons.lang.ResolverI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ChildTotalWidthResolver extends CursorBasedResolver<Long> implements
		ResolverI<ByteCursorI, Long> {

	public ChildTotalWidthResolver() {
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

	public Long resolveInternal(ByteCursorI cur) {
		cur.push();
		try {

			ByteCursorI s = cur.firstChild();
			int rt = 0;
			while (s != null) {
				rt += s.getLength();
				s = s.right();
			}

			return (long) rt;
		} finally {
			cur.pop();
		}

	}
}
