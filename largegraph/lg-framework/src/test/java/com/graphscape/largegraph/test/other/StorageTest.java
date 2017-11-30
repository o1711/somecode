/**
 * Jan 4, 2014
 */
package com.graphscape.largegraph.test.other;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.DefaultFileManager;
import com.graphscape.commons.record.Position;
import com.graphscape.commons.record.RecordI;
import com.graphscape.commons.record.RecordType;
import com.graphscape.commons.record.TxListenerI;
import com.graphscape.commons.record.TxStorageI;
import com.graphscape.commons.record.TxStorageFactoryI;
import com.graphscape.commons.record.provider.storage.DefaultTxStorageManager;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class StorageTest extends TestCase {

	public void test() {
		FileManagerI fm = new DefaultFileManager("mystorage");

		TxStorageFactoryI xstm = new DefaultTxStorageManager(fm);
		xstm.open();//

		final Map<Position, Position> changedPosMap = new HashMap<Position, Position>();

		TxStorageI xst1 = xstm.openTransaction(new TxListenerI() {

			@Override
			public void onRecordPositionChanged(Position oldPos, Position newPos) {
				changedPosMap.put(oldPos, newPos);
			}
		});

		TxStorageI xst2 = xstm.openTransaction();
		long start = System.currentTimeMillis();

		try {

			byte[] content = new byte[] { 0, 1, 2, 3 };
			RecordType type = RecordType.valueOf((byte) 1);

			Position pos = xst1.addRecord(type, content);
			RecordI record = xst1.getRecord(pos);
			assertEquals(type, record.getType());
			assertEquals(content, record.getContent());

			RecordI recInTrans2 = xst2.getRecord(pos);
			assertNull("record should not in transaction 2", recInTrans2);
			// less
			content = new byte[] { 0, 1 };
			xst1.updateRecord(pos, type, content);
			record = xst1.getRecord(pos);
			assertEquals(type, record.getType());
			assertEquals(content, record.getContent());
			// more
			content = new byte[] { 0, 1, 2, 3, 4, 5, 6 };
			xst1.updateRecord(pos, type, content);
			record = xst1.getRecord(pos);
			assertEquals(type, record.getType());
			assertEquals(content, record.getContent());
			xst1.commit();
			long end = System.currentTimeMillis();
			System.out.println("time used:" + (end - start));
			//
			Position newPos = changedPosMap.get(pos);
			assertNotNull("position:" + pos + " should be changed after commit.", pos);
			pos = newPos;
			recInTrans2 = xst2.getRecord(pos);
			System.out.println("time used:" + (end - start));
			assertNotNull("record should saw in transaction 2:" + pos, recInTrans2);
		} catch (Exception e) {
			e.printStackTrace();
			xst1.rollback();
		}

		xst1.close();
	}

	static public void assertEquals(byte[] expected, byte[] actual) {
		assertEquals(expected.length, actual.length);

		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual[i]);
		}

	}
}
