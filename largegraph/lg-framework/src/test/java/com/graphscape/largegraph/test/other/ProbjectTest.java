/**
 * Jan 24, 2014
 */
package com.graphscape.largegraph.test.other;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.graphscape.commons.debug.ProfileI;
import com.graphscape.commons.debug.TimeLoggerI;
import com.graphscape.commons.debug.provider.DefaultProfile;
import com.graphscape.commons.debug.provider.DefaultTimeLogger;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.FileManagers;
import com.graphscape.commons.probject.ProbjectI;
import com.graphscape.commons.probject.ProbjectStorageFactoryI;
import com.graphscape.commons.probject.ProbjectStorageI;
import com.graphscape.commons.probject.provider.DefaultProbjectStorageFactory;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ProbjectTest extends TestCase {

	FileManagerI fm;
	TimeLoggerI logger;
	ProfileI profile;
	ProbjectStorageFactoryI psf;
	private int loop = 1;
	private int dataSize = 1000;

	@Override
	protected void setUp() throws Exception {
		fm = FileManagers.tempFileManager();
		fm.open();
		psf = new DefaultProbjectStorageFactory(fm);
		psf.open();
		this.profile = new DefaultProfile();
		this.profile.apply(psf);
		this.profile.maxDepth(10);
		this.profile.start();
		this.logger = new DefaultTimeLogger();
	}

	@Override
	protected void tearDown() throws Exception {
		fm.close();
		psf.close();
		this.profile.end();
		this.profile.dump();
	}

	public void test() {
		List<String> idList = new ArrayList<String>();
		for (int l = 0; l < loop; l++) {

			ProbjectStorageI ps = psf.openTransaction();

			for (int i = 0; i < dataSize; i++) {

				String id = "" + l + "-" + i;
				idList.add(id);//
				ProbjectI po = ps.add(id);
				po.setProperty("id", id);
			}
			ps.commit();
		}
//
//		for (int l = 0; l < loop; l++) {
//			ProbjectStorageI ps = psf.openTransaction();
//			for (int j = 0; j < dataSize; j++) {
//				String id = "" + l + "-" + j;
//				ProbjectI po2 = ps.get(id);
//				String value = (String) po2.getProperty("id");
//				Assert.assertEquals(id, value);
//			}
//			ps.commit();
//		}

	}

}
