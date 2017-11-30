/**
 * Jan 21, 2014
 */
package com.graphscape.commons.file.provider;

import java.io.File;
import java.util.Random;

import com.graphscape.commons.file.FileManagerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class FileManagers {
	private static Random random = new Random();

	public static FileManagerI tempFileManager() {

		String dir = System.getProperty("java.io.tmpdir");
		long num = random.nextLong();
		if (num < 0) {
			num = Math.abs(num);
		}
		dir += File.separator + "lg" + File.separator + num;
		return new DefaultFileManager(dir);

	}
}
