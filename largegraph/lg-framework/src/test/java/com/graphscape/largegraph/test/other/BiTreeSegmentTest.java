/**
 * Jan 30, 2014
 */
package com.graphscape.largegraph.test.other;

import org.junit.Assert;

import junit.framework.TestCase;

import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.DefaultByteWindow;
import com.graphscape.commons.file.provider.FileManagers;
import com.graphscape.commons.record.BiTreeSegmentI;
import com.graphscape.commons.record.provider.segments.tree.DefaultBinaryTreeSegment;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class BiTreeSegmentTest extends TestCase {
	private static final long NULL = -1;
	FileManagerI fm;
	BiTreeSegmentI tree;
	
	private int HEAD = 1;
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		fm = FileManagers.tempFileManager();

		fm.open();

		ByteWindowI window = new DefaultByteWindow("RB_NODE", 4);

		tree = DefaultBinaryTreeSegment.valueOf("test-bi-tree", HEAD,window, fm);
		this.tree.open();
	}

	
	public void test() {
		byte[] head1 = new byte[]{1};//
		byte[] head2 = new byte[]{2};//
		byte[] head3 = new byte[]{3};//
		
		
		byte[] content1 = ByteArrayUtil.writeInt(1);
		byte[] content2 = ByteArrayUtil.writeInt(2);
		byte[] content3 = ByteArrayUtil.writeInt(3);

		long root = tree.addRoot(head1,content1);
		byte[] content1_ = tree.get(root);

		Assert.assertTrue("expected:" + content1 + ",actural:" + content1_,
				ByteArrayUtil.isEquals(content1, content1_));
		
		long left = tree.addLeft(root,head2, content2);
		
		long right = tree.addRight(root, head3,content3);
		
		long left_ = tree.getLeftPointer(root);
		long right_ = tree.getRightPointer(root);
		
		Assert.assertEquals(left, left_);
		Assert.assertEquals(right, right_);
		long root_ = tree.removeParentLink(left);
		Assert.assertEquals(root, root_);
		
		left_ = tree.getLeftPointer(root);
		Assert.assertEquals(NULL, left_);
		
		

	}

	@Override
	protected void tearDown() {

		this.tree.close();

		fm.close();
		fm.delete();

	}

}
