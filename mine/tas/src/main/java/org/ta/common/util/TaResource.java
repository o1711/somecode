package org.ta.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.ta.common.config.TaException;

public class TaResource {

	public static TaResource valueOf(String name) {
		int idx = name.indexOf(":");
		String pro = name.substring(0, idx);
		String loc = name.substring(idx + 1);
		return new TaResource(pro, loc);
	}

	private String protocol;

	private String location;

	private String stringValue;

	private TaResource(String protocol, String location) {
		this.protocol = protocol;
		this.location = location;
		this.stringValue = this.protocol + ":" + this.location;
	}

	public boolean isFile() {
		return "file".equals(this.protocol);
	}

	public InputStream getInputStream() {
		if ("classpath".equals(this.protocol)) {
			return TaResource.class.getClassLoader().getResourceAsStream(this.location);
		} else if ("file".equals(this.protocol)) {
			try {
				String file = this.location;
				if(TaOsUtil.isWindows()){
					file = file.replace('/', File.separatorChar);					
				}
				return new FileInputStream(new File(file));
			} catch (FileNotFoundException e) {
				throw new TaException(e);
			}
		} else {
			throw new TaException("not supported:" + this.protocol);
		}
	}

	@Override
	public int hashCode() {
		return this.stringValue.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TaResource)) {
			return false;
		}
		TaResource res = (TaResource) obj;
		return this.protocol.equals(res.protocol) && this.location.equals(res.location);
	}

}
