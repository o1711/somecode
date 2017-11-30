/**
 * Jan 19, 2014
 */
package com.graphscape.largegraph.test.other;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.graphscape.commons.file.FileI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.provider.DefaultFileManager;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class SubRegionTest extends TestCase {
	protected FileManagerI fm;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		fm = new DefaultFileManager("ram://mystorage");

		fm.open();

	}

	@Override
	protected void tearDown() {

		fm.close();

	}

	public void test() {
		FileI file = fm.getOrCreateFile("test.tst", 128);//
		file.open();
		try {
			this.doTest(file);
		} catch (RuntimeException e) {
			throw e;
		}
		file.close();
		file.delete();

	}

	public void doTest(FileI file) {
		PlainRegionI r1 = file.addRegion("r1", 100);
		PlainRegionI sub1 = r1.subRegion(10, 50);
		Assert.assertEquals(50, sub1.getLength());
		sub1.writeInt(0, 1);
		int i1 = sub1.readInt(0);

		Assert.assertEquals(1, i1);

		PlainRegionI sub2 = sub1.subRegion(20, 10);
		Assert.assertEquals(10, sub2.getLength());
		sub2.writeInt(0, 2);
		int i2 = sub2.readInt(0);
		Assert.assertEquals(2, i2);

		int i12 = sub1.readInt(20);
		Assert.assertEquals(2, i12);

	}

}
