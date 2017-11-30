package com.graphscape.commons.file.provider;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.lang.ErrorInfos;

public class WindowValidator {
	DefaultByteWindow window;
	PlainRegionI region;
	long offset;

	public WindowValidator(DefaultByteWindow window, PlainRegionI region, long offset) {
		this.window = window;
		this.region = region;
		this.offset = offset;
	}

	public void execute(ErrorInfos eis) {
		ByteCursorI cur = this.window.open(region);
		
	}

}
