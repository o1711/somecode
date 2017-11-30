package org.ta.common.util;

import java.util.List;

public class TaPath {

	private String[] path;

	private boolean absolute;

	public String[] getPath() {
		return path;
	}

	public boolean isAbsolute() {
		return absolute;
	}

	public static TaPath toPath(String p) {
		boolean abs = p.startsWith("/");

		if (abs) {
			p = p.substring(1);
		}

		String[] ps = p.split("/");

		return new TaPath(abs, ps);
	}

	public TaPath(boolean absolute, String[] path) {
		this.path = path;
		this.absolute = absolute;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || (!(obj instanceof TaPath))) {
			return false;
		}
		TaPath p = (TaPath) obj;
		String[] v2 = p.path;
		if (this.path.length != v2.length) {
			return false;
		}
		for (int i = 0; i < v2.length; i++) {
			if (!TaObjectUtil.nullSafeEquals(v2[i], this.path[i])) {
				return false;
			}
		}
		return true;

	}

	/**
	 * @param names
	 * @return
	 */
	public static TaPath valueOf(List<String> names) {
		String[] path = names.toArray(new String[] {});
		return new TaPath(true, path);
	}
}
