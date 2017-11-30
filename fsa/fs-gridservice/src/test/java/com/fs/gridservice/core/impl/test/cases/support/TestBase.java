/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.gridservice.core.impl.test.cases.support;

import junit.framework.TestCase;

import com.fs.commons.api.SPIManagerI;
import com.fs.gridservice.core.api.DataGridI;
import com.fs.gridservice.core.api.DgFactoryI;

/**
 * @author wu
 * 
 */
public class TestBase extends TestCase {

	protected static SPIManagerI sm;

	protected DgFactoryI factory;

	protected DataGridI dg;

	@Override
	public void setUp() {
		if (sm == null) {

			sm = SPIManagerI.FACTORY.get();

			sm.load("/boot/test-spim.properties");
		}
		this.factory = sm.getContainer().finder(DgFactoryI.class).find(true);

		this.dg = this.factory.getInstance();

	}

	protected void dump() {
		System.out.println("\n\ndump:\nTODO");

	}

	public void tearDown() throws Exception {
		//sm.shutdown();//
	}

}
