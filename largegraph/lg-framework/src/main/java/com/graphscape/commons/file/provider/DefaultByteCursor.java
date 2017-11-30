/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider;

import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.support.ByteCursorSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultByteCursor extends ByteCursorSupport {

	/**
	 * @param window
	 * @param region
	 * @param left
	 */
	public DefaultByteCursor(ByteWindowI window, PlainRegionI region) {
		this(window, region, 0);//
	}

	public DefaultByteCursor(ByteWindowI window, PlainRegionI region, long absoluteOffset) {
		super(window, region, absoluteOffset);
		super.targetReader = new DefaultCursorReader(this, region);
		super.targetWriter = new DefaultCursorWriter(this, region);
		this.applyTracer(targetReader);
		this.applyTracer(targetWriter);
	}


}
