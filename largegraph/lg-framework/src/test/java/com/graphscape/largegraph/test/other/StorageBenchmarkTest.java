/**
 * Jan 4, 2014
 */
package com.graphscape.largegraph.test.other;

import java.util.Random;

import junit.framework.TestCase;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.DefaultFileManager;
import com.graphscape.commons.record.Position;
import com.graphscape.commons.record.RecordI;
import com.graphscape.commons.record.RecordType;
import com.graphscape.commons.record.StorageI;
import com.graphscape.commons.record.provider.storage.DefaultStorage;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class StorageBenchmarkTest extends TestCase {
	private static final Random RANDOM = new Random();

	public void test() {// TODO concurrent test.
		FileManagerI fm = new DefaultFileManager("mystorage");
		StorageI st = new DefaultStorage(fm);
		st.open();
		for (int i = 0; i < 100; i++) {

			byte[] content = randomContent();
			RecordType type = RecordType.valueOf((byte) 1);

			// add
			Position pos = st.addRecord(type, content);
			RecordI record = st.getRecord(pos);
			assertEquals(type, record.getType());
			assertEquals(content, record.getContent());

			content = randomContent();
			st.updateRecord(pos, type, content);
			record = st.getRecord(pos);
			assertEquals(type, record.getType());
			assertEquals(content, record.getContent());

		}
		st.close();
	}

	static byte[] randomContent() {
		int size = RANDOM.nextInt(256);
		byte[] rt = new byte[size];
		RANDOM.nextBytes(rt);
		return rt;
	}

	static public void assertEquals(byte[] expected, byte[] actual) {
		assertEquals(expected.length, actual.length);

		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}

	}
}
