/**
 * Jan 19, 2014
 */
package com.graphscape.largegraph.test.other;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import junit.framework.TestCase;

import com.graphscape.commons.debug.ProfileI;
import com.graphscape.commons.debug.TimeLoggerI;
import com.graphscape.commons.debug.provider.DefaultProfile;
import com.graphscape.commons.debug.provider.DefaultTimeLogger;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.DefaultFileManager;
import com.graphscape.commons.file.provider.FileManagers;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.HeapSegmentI;
import com.graphscape.commons.record.provider.segments.heap.DefaultHeapSegment;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class HeapSegmentTest extends TestCase {
	private static Random random = new Random();

	protected FileManagerI fm;

	protected HeapSegmentI heap;

	protected SortedMap<Integer, Long> pointerMap;

	protected SortedMap<Integer, byte[]> contentMap;

	protected int maxLength = 256;

	protected int dataSize = 100;//

	protected int loop = 100;

	protected boolean trace = false;
	
	protected boolean debug = true;
	
	protected boolean deleteFile = false;
	
	protected TimeLoggerI timeLogger;

	protected ProfileI profile;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		debug(System.getenv());
		
		fm = FileManagers.tempFileManager();

		fm.open();
		this.heap = DefaultHeapSegment.valueOf("myheap", fm);
		this.heap.open();
		this.timeLogger = new DefaultTimeLogger();
		profile = new DefaultProfile();
		boolean app = profile.apply(this.heap);
		if (!app) {
			throw new GsException("todo profile.");
		}
		this.profile.start();
	}

	@Override
	protected void tearDown() {

		trace("total:" + this.timeLogger.getTotal() + ",hits:" + this.timeLogger.getHits() + ",avg:"
				+ this.timeLogger.getAvg());

		this.heap.close();

		fm.close();
		fm.delete();
		this.profile.end();
		this.profile.dump();

	}

	private void log(int loop, Object msg) {
		// this.timeLogger.pauseCounting();
		if (this.trace) {
			trace(msg);
		}
		// this.timeLogger.resumeCounting();
	}

	private void log(final long pointer, final boolean add, final int loop, final int id, final byte[] content) {
		Object o = new Object() {

			@Override
			public String toString() {
				return (add ? "add" : "remove") + ":" + id + ",str:" + ByteArrayUtil.hex(content)
						+ ",pointer:" + pointer;
			}

		};
		log(loop, o);//

	}

	public void test() throws Exception {
		this.pointerMap = new TreeMap<Integer, Long>();
		this.contentMap = new TreeMap<Integer, byte[]>();

		int id = 0;

		for (int i = 0; i < loop; i++) {
			trace("loop:" + i + "/" + loop + ",adding...");

			this.timeLogger.reset();
			for (int j = 0; j < this.dataSize; j++) {
				byte[] contentI = this.randomString(maxLength);
				this.contentMap.put(id, contentI);
				this.timeLogger.beforeExecute();

				long pointer = this.heap.add(contentI);

				this.timeLogger.afterExecute();

				this.log(pointer, true, loop, id, contentI);

				this.pointerMap.put(id, pointer);

				id++;
			}
			trace("done," + dataSize + " have added to heap,new size of heap:" + this.pointerMap.size()
					+ ",timelog:" + this.timeLogger);

			this.timeLogger.reset();
			int some = this.pointerMap.size() / 2;// half to remove?
			trace("loop:" + i + "/" + loop + ",removing...");

			for (int j = 0; j < some; j++) {
				int i1 = this.contentMap.firstKey();
				int i2 = this.contentMap.lastKey();

				int rid = this.randomId(i1, i2);

				byte[] contentI = this.contentMap.remove(rid);
				if (contentI == null) {
					continue;
				}

				long pointer = this.pointerMap.remove(rid);

				byte[] contentIA = this.heap.get(pointer);
				if (!ByteArrayUtil.isEquals(contentI, contentIA)) {

					throw new GsException("expected:" + ByteArrayUtil.hex(contentI) + ",actural:"
							+ ByteArrayUtil.hex(contentIA));

				}
				this.timeLogger.beforeExecute();
				this.heap.remove(pointer);
				this.timeLogger.afterExecute();

				// TODO get by none-exist pointer
				// this.heap.get(pointer);
			}

			trace("done," + some + " removed from heap" + ",timelog:" + this.timeLogger);

		}

	}
	public void debug(Object obj){
		if(this.debug){
			System.out.println(obj);
			
		}
	}
	public void trace(Object obj) {
		if (this.trace) {
			System.out.println(obj);//
		}
	}

	public int randomId(int from, int to) {
		int max = (int) (to - from);
		int rt = random.nextInt(max);
		return from + rt;
	}

	public byte[] randomString(int maxSize) {
		int size = random.nextInt(maxSize);
		byte[] rt = new byte[size];
		random.nextBytes(rt);
		return rt;

	}

}
