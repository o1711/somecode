package com.graphscape.commons.file;

import java.io.Writer;

public interface DumpableI {

	public void dump(Writer writer);
	
	public void dump();
}
