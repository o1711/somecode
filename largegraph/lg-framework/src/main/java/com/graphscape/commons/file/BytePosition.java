package com.graphscape.commons.file;

import java.util.List;

public interface BytePosition {
	
	public ByteWindowI getWindow();

	public int getId();

	public List<Integer> getParentIdList();

}
