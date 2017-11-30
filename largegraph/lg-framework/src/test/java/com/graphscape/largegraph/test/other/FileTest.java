/**
 * Jan 12, 2014
 */
package com.graphscape.largegraph.test.other;

import org.junit.Assert;

import junit.framework.TestCase;

import com.graphscape.commons.file.FileI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.provider.DefaultFileManager;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class FileTest extends TestCase {
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
		try {
			this.doTest(file);
		} catch (RuntimeException e) {
			throw e;
		}
		file.delete();

	}

	public void doTest(FileI file) {
		/*
		 * {
		 * 
		 * System.out.println("open,dump empty file and close file");
		 * file.open(); file.dump(); file.close(); } System.out.println(); {
		 * file.open();
		 * System.out.println("dump empty file and close once more");
		 * file.dump(); file.close(); } System.out.println();
		 */
		{

			System.out.println("open file,add reigion:r1:");
			file.open();
			long cap = 10;
			PlainRegionI r1 = file.addRegion("r1", cap);

			r1 = file.getRegion("r1");
			Assert.assertEquals(cap, r1.getCapacity());
			Assert.assertEquals(0L, r1.getLength());
			Assert.assertNotNull("region lost?", r1);

			r1.writeInt(0, 1);
			Assert.assertEquals(4L, r1.getLength());
			int one = r1.readInt(0);
			Assert.assertEquals(1, one);//

			System.out.println("dump file after add region:r1");
			file.dump();
			file.close();
		}
		System.out.println();
		{
			file.open();
			PlainRegionI r1 = file.getRegion("r1");
			file.dump();
			int r1_1 = r1.readInt(0);
			assertEquals(1, r1_1);
			file.close();
		}
	}
}
