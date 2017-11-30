/**
 * Jan 4, 2014
 */
package com.graphscape.largegraph.test.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.DefaultFileManager;
import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.provider.comparators.IntComparator;
import com.graphscape.commons.record.provider.index.rbtree.RedBlackTreeIndex;
import com.graphscape.commons.record.provider.index.rbtree.RedBlackTreeValidator;
import com.graphscape.commons.record.provider.serializer.IntSerializer;
import com.graphscape.commons.record.provider.serializer.StringSerializer;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class RedBlackTreeIndexTest extends TestCase {

	protected FileManagerI fm;
	protected RedBlackTreeIndex<Integer, String> idx;
	protected boolean isDump = false;
	protected List<Integer> keyList;
	protected RedBlackTreeValidator validator = new RedBlackTreeValidator();

	int randomKeyScope = 20000;// Integer.MAX_VALUE/1024;
	int randomDataSize = 1000;
	int loop = 10;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		fm = new DefaultFileManager("ram://mystorage");

		fm.open();
		idx = RedBlackTreeIndex.valueOf("myindex", new IntComparator(),
				new IntSerializer(), new StringSerializer(), fm);
		idx.open();
	}

	@Override
	protected void tearDown() {
		idx.close();
		fm.close();
	}

	public void test() {
		try {

			int[] data = new int[] { 40, 11, 16, 45, 19, 21, 73, 72, 75, 43 };
			data = new int[] { 40, 11, 16, 45, 19, 21 };//
			data = new int[] { 11, 2, 7, 14, 8, 17, 13, 5, 6, 12 };
			data = new int[] { 0, 16, 10, 11 };
			data = null;

			if (data != null) {
				this.populate(data);
				this.doTest(1, false);
			} else {
				this.xtestRandomData(loop, false, false);//
			}
		} finally {
			this.idx.dump();
		}

	}

	public void xtestRandomData(int loop, boolean onlyAdd,
			boolean clearBeforeEachLoop) {

		// this.populate(5, 23, 53, 54, 99, 4, 51, 19, 6, 80);
		//
		for (int i = 0; i < loop; i++) {
			System.out.println("loop:" + i + "/" + loop);
			this.populate();
			this.doTest(i, true);
			if (!onlyAdd) {
				this.doTest(i, false);
			}
			if (clearBeforeEachLoop) {
				this.idx.clear();
			}
		}

	}

	private void populate() {
		if (this.randomDataSize > this.randomKeyScope) {
			throw new GsException("dead loop");
		}

		this.keyList = new ArrayList<Integer>();

		Random random = new Random();

		while (keyList.size() < this.randomDataSize) {
			Integer k = random.nextInt(randomKeyScope);
			if (keyList.contains(k)) {
				continue;
			}
			keyList.add(k);
		}
	}

	private void populate(int... ks) {
		this.keyList = new ArrayList<Integer>();

		for (int i = 0; i < ks.length; i++) {
			keyList.add(ks[i]);
		}

	}

	public void doTest(int loop, boolean onlyAdd) {

		System.out.println("loop:" + loop + " test data len:" + keyList.size()
				+ "," + (onlyAdd ? "onlyAdd" : " and/remove"));
		System.out.println("keyList:" + keyList);
		long start = System.currentTimeMillis();

		for (int i = 0; i < keyList.size(); i++) {
			Integer k = keyList.get(i);
			this.doAdd(i, k);
		}
		if (!onlyAdd) {
			for (int i = 0; i < keyList.size(); i++) {
				Integer k = keyList.get(i);
				this.doRemove(i, k);
			}
		}

		long end = System.currentTimeMillis();

		System.out.println("time consumption:" + (end - start) + "/"
				+ keyList.size() + ",avg:" + ((end - start)) / keyList.size());

	}

	public void doAdd(int i, Integer k) {
		String v = k + "V";
		idx.put(k, v);
		this.validate(true, k);
		String v2 = idx.get(k);
		// this.print();
		Assert.assertEquals("value:" + v2 + " for key:" + k + " not expected:"
				+ v, v, v2);

	}

	protected void validate(boolean add, Integer k) {

		ErrorInfos eis = this.validator.validate(idx);
		if (eis.hasError()) {
			eis.add("after " + (add ? "add" : "remove") + "key:" + k
					+ " the tree is crashed.");
			System.out.println(eis);//
		}
		eis.assertNoError();

	}

	public void dump(String title) {
		if (this.isDump) {
			System.out.println(title);
			this.idx.dump();

		}
	}

	public void doRemove(int i, Integer k) {
		String v = k + "V";

		this.dump("before remove:" + k);

		String oldV = idx.get(k);

		String v2 = idx.remove(k);

		this.dump("after remove:" + k);

		this.validate(false, k);//

		Assert.assertEquals("remove failure, data item:" + i + ":" + k
				+ ",expected value:" + v + ",actual:" + v2, v, v2);

		v2 = idx.get(k);
		Assert.assertNull("remove failure, data item:" + i + ":" + k
				+ ",expected null,actual:" + v2, v2);

	}

	static public void assertEquals(byte[] expected, byte[] actual) {
		assertEquals(expected.length, actual.length);

		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}

	}

}
/**
 * <code>
 * 
 * -------------------------------------------------
 * +50:
 * 			  	 50(B)
 * 		    	/    \	
 * 
 * -------------------------------------------------
 * +25:
 * 			  	  50(B)
 * 		    	/      \	
 * 		      25(R)		 
 * 			/	 \		
 * -------------------------------------------------
 * +75:
 * 			  	  50(B)
 * 		    	/      \	
 * 		      25(R)		75(R) 
 * 			/	 \		/	\
 * 
 * ------------------------------------------------- 
 * +10:
 * 			  	   50(B)
 * 		    	 /      \	
 * 		      25(R)		75(R) 
 * 			/	 \		/	\
 * 		*10(R)	  
 * 		/	\		
 * ------------------------------------------------- 
 * +10:1
 * 			  	   
 * 			  	   50(B)
 * 		    	 /      \	
 * 		      25(B)		75(B) 
 * 			/	 \		/	\
 * 		*10(R)	  
 * 		/	\	
 * -------------------------------------------------    
 * +5:
 * 			  	   
 * 			  	   50(B)
 * 		    	 /      \	
 * 		      25(B)		75(B) 
 * 			/	 \		/	\
 * 		*10(R)	  
 * 		 /	\	
 *     5(R)
 *    /  \
 *    
 * -------------------------------------------------  
 * 
 * +5:1
 * 			  	   
 * 			  	   50(B)
 * 		    	 /      \	
 * 		      10(B)		75(B) 
 * 			/	 \		/	\
 * 		*5(R)	 25(B) 
 * 		 /	\	/
 * -------------------------------------------------  
 * 
 * +5:2
 * 			  	   
 * 			  	   50(B)
 * 		    	 /      \	
 * 		      10(B)		75(B) 
 * 			/	 \		/	\
 * 		*5(R)	 25(R) 
 * 		 /	\	/	\
 *    
 * -------------------------------------------------  
 * <code>
 */
