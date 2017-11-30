package com.graphscape.largegraph.test.other;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.FileManagers;
import com.graphscape.commons.record.ListsSegmentI;
import com.graphscape.commons.record.provider.segments.list.DefaultListsSegment;
import com.graphscape.commons.util.ByteArrayUtil;

public class ListsSegmentTest extends TestCase {

	protected FileManagerI fm;

	protected ListsSegmentI heap;

	public void test() {

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		fm = FileManagers.tempFileManager();

		fm.open();
		this.heap = DefaultListsSegment.valueOf("mylists", fm);
		this.heap.open();
	}

	@Override
	protected void tearDown() {

		this.heap.close();

		fm.close();
		fm.delete();

	}

}
