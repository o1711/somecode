package com.graphscape.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.graphscape.commons.lang.GsException;

public class Path {

	private static Map<String, Path> CACHE = new HashMap<String, Path>();

	private List<String> nameList;

	public static Path ROOT = Path.valueOf(new String[] {});

	public List<String> getNameList() {
		return nameList;
	}

	public String getName() {
		if (this.isRoot()) {
			return null;
		}
		return this.nameList.get(this.nameList.size() - 1);
	}

	public Path getPrefixParent(int maxDepth) {

		List<String> nl = null;
		if (this.nameList.size() > maxDepth) {
			nl = new ArrayList<String>(this.nameList.subList(0, maxDepth));
		} else {
			nl = new ArrayList<String>(this.nameList);
		}

		return new Path(nl);
	}

	private Path(String[] names) {
		this(Arrays.asList(names));
	}

	private Path(List<String> nl) {

		List<String> nl2 = new ArrayList<String>();
		for (String s : nl) {
			if (s == null) {
				throw new GsException("null path element,path:" + nl);
			}
			if (s.trim().length() == 0) {
				continue;
			}
			nl2.add(s);
		}
		this.nameList = nl2;

	}

	public boolean isRoot() {
		return this.nameList.isEmpty();
	}

	public Path getParent() {
		if (this.isRoot()) {
			return null;
		}
		List<String> ps = new ArrayList<String>();
		for (int i = 0; i < this.nameList.size() - 1; i++) {
			String name = this.nameList.get(i);
			ps.add(name);

		}
		return new Path(ps);
	}

	public int size() {
		return this.nameList.size();
	}

	public boolean isSubPath(Path p) {
		return this.isSubPath(p, false);
	}

	public boolean isSubPath(Path p, boolean include) {
		int s1 = this.size();
		int s2 = p.size();
		if (include && s1 > s2 || !include && s1 >= s2) {
			return false;
		}
		for (int i = 0; i < this.nameList.size(); i++) {
			String n1 = this.nameList.get(i);
			String n2 = p.nameList.get(i);
			if (!ObjectUtil.nullSafeEquals(n1, n2)) {
				return false;
			}
		}
		return true;

	}

	public Path getSubPath(String name) {
		List<String> names = new ArrayList<String>();
		names.addAll(this.nameList);

		names.add(name);

		return Path.valueOf(names);
	}

	public static Path valueOf(String name, Path p) {
		List<String> nl = new ArrayList<String>(p.getNameList());
		nl.add(0, name);
		return new Path(nl);
	}

	public static Path valueOf(Path par, String name) {
		return par.getSubPath(name);
	}

	public static Path valueOf(List<String> names) {
		return new Path(names);
	}

	public static Path valueOf(String[] names) {
		return valueOf(Arrays.asList(names));

	}

	public static Path valueOf(String name) {
		Path rt = CACHE.get(name);
		if (rt != null) {
			return rt;
		}
		rt = valueOf(name, '/');
		CACHE.put(name, rt);
		return rt;

	}

	public static Path valueOf(String name, char sep) {
		String[] names = name.split("" + sep);
		return valueOf(names);
	}
	
	public boolean isEndWith(String name){
		if(this.nameList.isEmpty()){
			return false;
		}
		return ObjectUtil.nullSafeEquals(name, this.nameList.get(this.nameList.size()-1));
	}	

	public boolean isEndWith(Path p) {
		List<String> nL = p.nameList;
		if (this.nameList.size() < nL.size()) {
			return false;
		}
		for (int i = 0; i < nL.size(); i++) {
			int j = nL.size() - i - 1;
			int x = this.nameList.size() - i - 1;
			String name1 = nL.get(j);
			String name2 = this.nameList.get(x);
			if (!name1.equals(name2)) {
				return false;
			}
		}
		return true;
	}

	public Path subPath(int fromIdx) {
		if (fromIdx < 0 || fromIdx > this.nameList.size()) {
			throw new GsException("index out out range:" + fromIdx + ",size:"
					+ this.nameList.size());
		}
		List<String> nms = new ArrayList<String>(this.nameList.subList(fromIdx,
				this.nameList.size()));
		return Path.valueOf(nms);
	}

	public Path concat(Path p) {
		List<String> nameL = new ArrayList<String>(this.nameList);
		nameL.addAll(p.nameList);
		return Path.valueOf(nameL);
	}

	public int length() {
		return this.nameList.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Path)) {
			return false;
		}
		Path p2 = (Path) obj;
		if (p2.size() != this.size()) {
			return false;
		}
		for (int i = 0; i < this.nameList.size(); i++) {
			String o1 = this.nameList.get(i);
			String o2 = p2.nameList.get(i);

			if (!ObjectUtil.nullSafeEquals(o1, o2)) {
				return false;
			}

		}
		return true;
	}

	public String toString(char sep) {
		String rt = "";
		for (String n : this.nameList) {
			rt += sep;
			rt += n;
		}
		return rt;
	}

	/*
	
	 */
	@Override
	public String toString() {
		//
		return toString('/');
	}

	/*
	 * Dec 23,
	 */
	@Override
	public int hashCode() {
		//
		return this.nameList.hashCode();
	}

	/**
	 * @param contextPath
	 * @return
	 */
	public boolean isStartWith(Path path) {
		List<String> nL = path.nameList;
		if (nL.size() > this.nameList.size()) {
			return false;
		}
		for (int i = 0; i < nL.size(); i++) {
			if (!ObjectUtil.nullSafeEquals(nL.get(i), this.nameList.get(i))) {
				return false;
			}
		}
		return true;
	}

}
