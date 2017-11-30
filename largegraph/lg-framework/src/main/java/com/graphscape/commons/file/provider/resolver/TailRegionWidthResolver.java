package com.graphscape.commons.file.provider.resolver;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.support.CursorBasedResolver;
import com.graphscape.commons.lang.ResolverI;

public class TailRegionWidthResolver extends CursorBasedResolver<Long> implements
		ResolverI<ByteCursorI, Long> {

	public TailRegionWidthResolver() {
	}

	@Override
	public Long resolve(ByteCursorI s) {

		long aoff = s.getAbsoluteOffset();
		long len = s.getRegion().getLength();
		return len - aoff;

	}

}
