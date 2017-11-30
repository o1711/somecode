/**
 * Jan 12, 2014
 */
package com.graphscape.largegraph.test.other;

import java.io.StringWriter;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.graphscape.commons.debug.TracerI;
import com.graphscape.commons.debug.provider.DefaultTracer;
import com.graphscape.commons.file.ByteCursorI;
import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.PlainRegionI;
import com.graphscape.commons.file.RootByteFormaterI;
import com.graphscape.commons.file.provider.DefaultByteWindow;
import com.graphscape.commons.file.provider.DefaultFileManager;
import com.graphscape.commons.file.provider.MemoryPlainRegion;
import com.graphscape.commons.file.provider.formaters.DefaultByteFormater;
import com.graphscape.commons.file.provider.resolver.ChildTotalWidthResolver;
import com.graphscape.commons.file.support.ProxyPlainRegion;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.lang.HandlerI;
import com.graphscape.commons.record.provider.serializer.IntSerializer;
import com.graphscape.commons.record.provider.serializer.LongSerializer;
import com.graphscape.commons.record.provider.serializer.ShortSerializer;

/**
 * <code>
 * 
 * ROOT:
 * +------------------------+
 * | TOP1 		| TOP2 		|
 * |------------------------|
 * | 4 bytes	| TOP1* ?	|
 * +------------------------+
 * 
 * TOP2:
 * +--------------------------------------------+
 * | BOT1 		| BOT2		| BOT3				| 
 * |--------------------------------------------|
 * | 4 bytes	| 2 bytes	| BOT2 * 8 bytes	|
 * +--------------------------------------------+
 * 
 * 
 * </code>
 * 
 * @author wuzhen0808@gmail.com
 * 
 */
public class ByteWindowTest extends TestCase {
	protected FileManagerI fm;

	protected TracerI tracer = new DefaultTracer();

	static class TracerListener implements HandlerI<TracerI> {

		@Override
		public void handle(TracerI t) {

		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		fm = new DefaultFileManager("ram://mystorage");

		fm.open();
		this.tracer.addAfterPushListener(new TracerListener());
		this.tracer.disable();//
	}

	@Override
	protected void tearDown() {

		fm.close();

	}

	public void test() {
		PlainRegionI r1 = new MemoryPlainRegion();
		try {
			int offset = 10;
			this.doTest(offset, r1);
		} catch (RuntimeException e) {
			System.out.println("dumping region before throwing exception.");
			r1.dump();
			throw e;
		} finally {

		}

	}

	public void doTest(int offset, PlainRegionI region) {

		PlainRegionI r1 = new ProxyPlainRegion(region, offset);

		int TOP1 = 4;// second layer1, pont out the repeat of second layer 2
		int HED1 = 4;
		int HED2 = 2;
		int HED3 = 8;

		int repeatOfSecondLayer2 = 2;
		int repeatOfTOP2_0_HED3 = 3;
		int repeatOfTOP2_1_HED3 = 2;

		int offsetTop2_0 = (int) offset + TOP1;
		int lengthTop2_0 = HED1 + HED2 + repeatOfTOP2_0_HED3 * HED3;

		int offsetTop2_1 = (int) offset + TOP1 + lengthTop2_0;
		int lengthTop2_1 = HED1 + HED2 + repeatOfTOP2_1_HED3 * HED3;

		int lengthTop2 = lengthTop2_0 + lengthTop2_1;
		int lengthRoot0 = TOP1 + lengthTop2;

		int regionLength = (int) offset + lengthRoot0;

		// init window definition:
		ByteWindowI secondLayer1 = new DefaultByteWindow("TOP1", TOP1).serializer(new IntSerializer());

		ByteWindowI secondLayer2 = new DefaultByteWindow("TOP2", new ChildTotalWidthResolver(), "TOP1");

		secondLayer2.addChild(new DefaultByteWindow("BOT1", HED1).serializer(new IntSerializer()))//
				.addChild(new DefaultByteWindow("BOT2", HED2).serializer(new ShortSerializer()))//
				.addChild(new DefaultByteWindow("BOT3", HED3, "BOT2").serializer(new LongSerializer()));

		ByteWindowI WINDOW = new DefaultByteWindow("ROOT")//
				.addChild(secondLayer1)//
				.addChild(secondLayer2);

		// boolean trace = this.tracer.apply(WINDOW);//
		// open cursor and test read write.
		ByteCursorI cur = WINDOW.open(region, offset);
		{
			cur.firstChild();// ROOT(0)/TOP1(0)
			Assert.assertEquals("TOP1", cur.getPointer().getWindow().getName());
			Assert.assertTrue("should be first child", cur.isFirstChild());
			cur.writeInt(0, repeatOfSecondLayer2);//
			long len = cur.getLength();
			Assert.assertEquals(TOP1, len);
		}
		{
			cur.right();// ROOT(0)/TOP2(0)
			Assert.assertEquals("TOP2", cur.getPointer().getWindow().getName());
			Assert.assertEquals(offsetTop2_0, cur.getAbsoluteOffset());
			Assert.assertEquals(0, cur.getPointer().getId());
			Assert.assertFalse("should be second child", cur.isFirstChild());
			this.doTestSecondLayer(0, offset, cur, (short) 3);
			long acturalLen = cur.getLength();
			Assert.assertEquals(lengthTop2_0, acturalLen);

		}
		{
			cur.right();// ROOT(0)/TOP2(1)
			Assert.assertEquals("TOP2", cur.getPointer().getWindow().getName());
			Assert.assertEquals(1, cur.getPointer().getId());
			Assert.assertTrue("should be last child", cur.isLastChild());
			Assert.assertEquals(offsetTop2_1, cur.getAbsoluteOffset());
			this.doTestSecondLayer(1, offset, cur, (short) 2);
			long acturalLen = cur.getLength();
			Assert.assertEquals(lengthTop2_1, acturalLen);
		}

		{// reset to root
			cur.root();
			long rootOffset = cur.getAbsoluteOffset();
			Assert.assertEquals(offset, rootOffset);
			long rootLength = cur.getLength();
			Assert.assertEquals(lengthRoot0, rootLength);
		}
		long regionLen = region.getLength();
		Assert.assertEquals(regionLength, regionLen);

		RootByteFormaterI formaer = new DefaultByteFormater();

		StringWriter sb = new StringWriter();

		formaer.format(true, WINDOW, r1, sb);//

		String formated = sb.toString();
		String[] formatedArray = formated.split("\n");
		Assert.assertEquals(1 + 3, formatedArray.length);
		{

			String expectedHeader = " [TOP1    ] [BOT1    ] [BOT2]  [BOT3            ]  [BOT3            ]  [BOT3            ]  [BOT1    ]  [BOT2]  [BOT3            ]  [BOT3            ]";
			String acturalHeader = formatedArray[1];

			Assert.assertEquals(expectedHeader, acturalHeader);
		}
		{
			String expectedHeader = " [2       ] [1       ] [3   ]  [30              ]  [31              ]  [32              ]  [1       ]  [2   ]  [30              ]  [31              ]";
			String acturalHeader = formatedArray[2];
			Assert.assertEquals(expectedHeader, acturalHeader);
		}
		{
			String expectedHeader = "0[00000002]4[00000001]8[0003]10[000000000000001e]18[000000000000001f]26[0000000000000020]34[00000001]38[0002]40[000000000000001e]48[000000000000001f]56";
			String acturalHeader = formatedArray[3];
			Assert.assertEquals(expectedHeader, acturalHeader);
		}

		System.out.println("formated:\n" + sb.getBuffer());
		// dump reigion after test
		StringWriter sb2 = new StringWriter();
		region.dump(sb2);
		System.out.println("dump region finally:");
		System.out.println(sb2);

		String expectedDump = "0000000000000000000000000002000000010003000000000000001e000000000000001f0000000000000020000000010002000000000000001e000000000000001f";
		String acturalDump = sb2.toString();
		Assert.assertEquals(expectedDump, acturalDump);

	}

	private void doTestSecondLayer(int top2Index, long offset, ByteCursorI cur, short repeatHed3) {
		// test the basic function:
		if (repeatHed3 <= 0) {
			throw new GsException("repeat negative:" + repeatHed3);
		}
		cur.push();
		try {
			cur.firstChild();
			Assert.assertEquals("BOT1", cur.getPointer().getWindow().getName());
			Assert.assertEquals("BOT2", cur.right().getPointer().getWindow().getName());
			Assert.assertEquals("BOT3", cur.right().getPointer().getWindow().getName());

		} finally {
			cur.pop();
		}
		// test write by cursor:
		cur.push();
		try {
			cur.firstChild();
			cur.writeInt(0, 1);// HED1
			cur.right();
			cur.writeShort(0, repeatHed3);// HED2
			Assert.assertEquals("{/ROOT(0)/TOP2(" + top2Index + ")/BOT2(0)}", cur.getPointer().toString());
			if (top2Index == 0) {
				Assert.assertEquals(offset + 8, cur.getAbsoluteOffset());//
			} else if (top2Index == 1) {
				Assert.assertEquals(offset + 38, cur.getAbsoluteOffset());//
			}

			int rep3 = cur.readShort(0);
			Assert.assertEquals("read or write error.", repeatHed3, rep3);
			StringWriter sw = new StringWriter();

			System.out.println(sw.toString());

			for (int i = 0; i < repeatHed3; i++) {
				cur.right().writeLong(0, 30 + i);// HED3-1
			}

		} finally {
			cur.pop();
		}
		cur.push();
		try {
			cur.firstChild();
			Assert.assertEquals(1, cur.readInt(0));
			Assert.assertEquals(repeatHed3, cur.right().readShort(0));//
			for (int i = 0; i < repeatHed3; i++) {
				Assert.assertEquals(30 + i, cur.right().readLong(0));//
			}

		} finally {
			cur.pop();
		}

	}
}
