/**
 * Jan 20, 2014
 */
package com.graphscape.commons.debug.provider;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import com.graphscape.commons.debug.ProfileAwareI;
import com.graphscape.commons.debug.ProfileI;
import com.graphscape.commons.debug.TimeLoggerI;
import com.graphscape.commons.util.StringUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class DefaultProfile implements ProfileI {

	private abstract static class Entry {

		protected String id;

		public Entry(String id) {
			this.id = id;
		}

		public abstract long getHits();

		public abstract long getTotalMillis();

		public abstract long getInternalAvgMicros();

		public abstract long getInternalMillis();

		public abstract long getExternalMillis();

	}

	private static class FlatEntry extends Entry {
		long hits;
		long totalMillis;
		long internalMilis;
		long externalMillis;

		public FlatEntry(String id) {
			super(id);
		}

		public void plus(StackEntry se) {
			this.hits += se.getHits();
			this.totalMillis += se.getTotalMillis();
			this.internalMilis += se.getInternalMillis();
			this.externalMillis += se.getExternalMillis();
		}

		@Override
		public long getHits() {
			return hits;
		}

		@Override
		public long getTotalMillis() {
			return totalMillis;
		}

		@Override
		public long getInternalAvgMicros() {
			return this.internalMilis * 1000 / hits;
		}

		@Override
		public long getInternalMillis() {
			return internalMilis;
		}

		@Override
		public long getExternalMillis() {
			// TODO Auto-generated method stub
			return externalMillis;
		}

	}

	private static class StackEntry extends Entry {

		private TimeLoggerI timeLogger;

		private Map<String, StackEntry> childMap = new HashMap<String, StackEntry>();

		public StackEntry(String id) {
			super(id);
			this.timeLogger = new DefaultTimeLogger();
		}

		public StackEntry getOrCreate(String id) {
			StackEntry en = this.childMap.get(id);
			if (en == null) {
				en = new StackEntry(id);
				this.childMap.put(id, en);
			}
			return en;

		}

		public void enter() {
			this.timeLogger.beforeExecute();
		}

		public void exit() {
			this.timeLogger.afterExecute();
		}

		public long getHits() {
			return this.timeLogger.getHits();
		}

		public long getTotalMillis() {
			return this.timeLogger.getTotal().toMillis();
		}

		public long getInternalAvgMicros() {
			long hits = this.getHits();
			if (hits == 0) {
				return 0;
			}
			return (this.getTotalMillis() - this.getExternalMillis()) * 1000 / this.getHits();

		}

		public long getInternalMillis() {
			long ext = this.getExternalMillis();
			return this.getTotalMillis() - ext;
		}

		public long getExternalMillis() {
			long childTime = 0;
			for (StackEntry en : this.childMap.values()) {
				childTime += en.getTotalMillis();
			}
			return childTime;
		}

		public void addHits(int hits) {
			this.timeLogger.addHits(hits);
		}

	}

	private int[] maxColumnWidth = new int[] { 4, 8, 8, 8, 8, 8, 8, 8, 8 };

	private int maxDepth = 100;

	private StackEntry root;

	private Stack<StackEntry> stack = new Stack<StackEntry>();

	@Override
	public void beforeExecute(String id) {
		StackEntry parent = this.stack.peek();
		StackEntry en = parent.getOrCreate(id);
		en.enter();
		this.stack.push(en);
		this.root.addHits(1);
	}

	@Override
	public void afterExecute() {
		StackEntry en = this.stack.pop();
		en.exit();
	}

	@Override
	public void dump() {
		this.dumpStack();
		this.dumpFlat();
	}

	protected void dumpFlat() {
		Map<String, FlatEntry> map = new HashMap<String, FlatEntry>();
		this.forEach(root, map);
		FlatEntry[] feA = map.values().toArray(new FlatEntry[] {});
		Arrays.sort(feA, new Comparator<FlatEntry>() {

			@Override
			public int compare(FlatEntry o1, FlatEntry o2) {
				return (int) (o2.internalMilis - o1.internalMilis);
			}
		});

		this.doDumpHeader("_______flat_order_by_internal_millis_________");
		AtomicInteger row = new AtomicInteger(-1);
		for (FlatEntry fe : feA) {
			this.doDump(1, row, fe);
		}
		System.out.println("______________________________________");
		
	}

	protected void forEach(StackEntry se, Map<String, FlatEntry> map) {
		FlatEntry fe = map.get(se.id);
		if (fe == null) {
			fe = new FlatEntry(se.id);
			map.put(se.id, fe);
		}
		fe.plus(se);
		for (StackEntry seI : se.childMap.values()) {
			forEach(seI, map);
		}
	}

	public void dumpStack() {
		this.doDumpHeader("______stack_entry_dump__________");
		this.dump(0, new AtomicInteger(-1), this.root);
		System.out.println("______________________________________");
		
	}

	public String columnDepth(int depth, String value) {
		return StringUtil.space(depth + 1) + StringUtil.fillSpace(value, 4 + maxDepth - depth);

	}

	public String column(int column, Object value) {
		return StringUtil.fillSpace(maxColumnWidth[column], "" + value);
	}

	public void dump(int depth, AtomicInteger row, StackEntry en) {
		if (depth > this.maxDepth) {
			return;
		}
		this.doDump(depth, row, en);
		for (StackEntry child : en.childMap.values()) {
			dump(depth + 1, row, child);
		}

	}

	private void doDumpHeader(String line) {
		System.out.println(line);//
		StringBuffer sb = new StringBuffer();
		sb.append(column(0, "ROW"));
		sb.append(columnDepth(0, "DPTH"));
		sb.append(column(2, "TOT(mS)"));
		sb.append(column(3, "INT(mS)"));
		sb.append(column(4, "HIT"));
		sb.append(column(5, "AVG(uS)"));
		sb.append(column(6, "EXT(mS)"));
		sb.append(column(7, "ID"));
		System.out.println(sb);//
	}

	private void doDump(int depth, AtomicInteger row, Entry en) {
		StringBuffer sb = new StringBuffer();
		int col = 0;
		sb.append(column(col++, row.incrementAndGet()));
		sb.append(columnDepth(depth, "(" + depth + ")"));
		//
		sb.append(column(col++, en.getTotalMillis()));
		sb.append(column(col++, en.getInternalMillis()));
		sb.append(column(col++, en.getHits()));
		sb.append(column(col++, en.getInternalAvgMicros()));
		sb.append(column(col++, en.getExternalMillis()));
		sb.append("  (");
		sb.append(en.id);
		sb.append("\n");
		System.out.print(sb);
	}

	@Override
	public void reset() {
		this.root.childMap.clear();
	}

	@Override
	public void start() {
		this.root = new StackEntry("root");
		this.root.enter();
		this.stack.push(root);
	}

	@Override
	public void end() {
		StackEntry root = this.stack.pop();
		root.exit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.debug.ProfileI#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(Object obj) {
		if (!(obj instanceof ProfileAwareI)) {
			return false;
		}
		((ProfileAwareI) obj).setProfile(this);
		return true;
	}

	@Override
	public boolean applyAll(List<Object> childToBeApplied) {
		boolean rt = true;
		for (Object o : childToBeApplied) {
			if (!this.apply(o)) {
				rt = false;
			}

		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.graphscape.commons.debug.ProfileI#maxDepth(int)
	 */
	@Override
	public void maxDepth(int i) {
		this.maxDepth = i;
	}

}
