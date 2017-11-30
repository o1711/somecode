package com.graphscape.commons.file.support;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.graphscape.commons.debug.support.TracableSupport;
import com.graphscape.commons.file.DumpableI;
import com.graphscape.commons.lang.GsException;

public abstract class DumpableSupport extends TracableSupport implements DumpableI {

	@Override
	public void dump() {
		OutputStreamWriter writer = new OutputStreamWriter(System.out);
		this.dump(writer);
		try {
			writer.flush();
		} catch (IOException e) {
			throw new GsException(e);
		}
	}

	@Override
	public void dump(Writer writer) {
		try {
			this.dumpInternal(writer);
		} catch (IOException e) {
			throw GsException.toRuntimeException(e);
		}

	}

	public abstract void dumpInternal(Writer writer) throws IOException;

}
