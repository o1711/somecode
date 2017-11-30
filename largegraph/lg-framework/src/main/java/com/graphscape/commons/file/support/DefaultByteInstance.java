package com.graphscape.commons.file.support;

import java.util.ArrayList;
import java.util.List;

import com.graphscape.commons.file.BytePosition;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.lang.GsException;

public class DefaultByteInstance implements BytePosition {

	private List<Integer> parentIdList = new ArrayList<Integer>();
	private ByteWindowI window;
	private int id;

	@Override
	public ByteWindowI getWindow() {
		// TODO Auto-generated method stub
		return this.window;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public List<Integer> getParentIdList() {

		return this.parentIdList;
	}

	public static DefaultByteInstance valueOf(ByteWindowI window, int instance) {
		DefaultByteInstance rt = new DefaultByteInstance();
		rt.window = window;
		rt.id = instance;
		return rt;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		int i = this.parentIdList.size() - 1;
		for (ByteWindowI pwin = this.window.getParent(); pwin != null; pwin = pwin.getParent()) {
			if(i ==-1){
				throw new GsException("bug");
			}
			StringBuffer sb2 = new StringBuffer();
			sb2.append("/");
			sb2.append(pwin.getName());
			sb2.append("(");
			sb2.append(this.parentIdList.get(i));
			sb2.append(")");			
			sb.insert(0, sb2);//
			i --;
		}

		sb.insert(0, "{");
		sb.append("/");
		sb.append(this.window.getName());
		sb.append("(");
		sb.append(id + ")");
		sb.append("}");
		return sb.toString();
	}

	@Override
	public DefaultByteInstance clone() {
		DefaultByteInstance rt = new DefaultByteInstance();
		rt.window = this.window;
		rt.id = this.id;
		rt.parentIdList = new ArrayList<Integer>(this.parentIdList);
		return rt;
	}

	public boolean moveToParent() {
		if (this.parentIdList.isEmpty()) {
			return false;
		}
		this.window = this.window.getParent();
		this.id = this.parentIdList.remove(this.parentIdList.size() - 1);//
		return true;
	}

	public void set(ByteWindowI window, int id) {
		this.window = window;
		this.id = id;
	}

	public boolean moveToLeft(ByteCursorSupport cur) {
		if (this.id > 0) {
			this.id--;
			return true;
		}

		ByteWindowI left = this.window.getLeft();
		if (left == null) {
			return false;
		}

		this.window = left;

		this.id = (int) this.window.getOccurs(cur) - 1;
		return true;
	}

	public boolean moveToFirstChild() {
		ByteWindowI bw = this.window.getFirstChild();
		if (bw == null) {
			return false;
		}
		this.parentIdList.add(this.id);// save
		this.window = bw;
		this.id = 0;

		if (bw.getName().equals("ROOT")) {
			throw new GsException("BUG");
		}
		return true;
	}

	public boolean isRoot() {
		return this.parentIdList.isEmpty();
	}

	public boolean isFirstChild() {
		return this.id == 0 && this.window.isFirstChild();
	}
	
	public DefaultByteInstance getParent() {
		if (this.parentIdList.isEmpty()) {
			return null;
		}

		DefaultByteInstance rt = new DefaultByteInstance();
		rt.window = this.window.getParent();
		rt.parentIdList = new ArrayList<Integer>(this.parentIdList);
		Integer pid = rt.parentIdList.remove(rt.parentIdList.size() - 1);
		rt.id = pid;
		return rt;
	}

	public void nextId() {
		this.id++;
	}
}
