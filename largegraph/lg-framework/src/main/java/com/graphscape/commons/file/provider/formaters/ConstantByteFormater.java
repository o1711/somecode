/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file.provider.formaters;

import java.io.IOException;
import java.io.Writer;

import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.support.LeafByteFormaterSupport;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ConstantByteFormater extends LeafByteFormaterSupport {
	private String value;

	public ConstantByteFormater(String constant) {
		this.value = constant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.graphscape.commons.file.support.LeafByteFormaterSupport#formatInternal
	 * (com.graphscape.commons.file.ByteCursorI, java.io.Writer)
	 */
	@Override
	protected void formatInternal(ByteCursorI cur, Writer writer) throws IOException {
		if (value != null) {
			writer.write(value);
		}

	}

}