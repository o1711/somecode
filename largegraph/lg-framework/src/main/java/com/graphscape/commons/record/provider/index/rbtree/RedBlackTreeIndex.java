package com.graphscape.commons.record.provider.index.rbtree;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphscape.commons.file.ByteWindowI;
import com.graphscape.commons.file.FileManagerI;
import com.graphscape.commons.file.provider.DefaultByteWindow;
import com.graphscape.commons.file.provider.formaters.ConstantByteFormater;
import com.graphscape.commons.file.provider.formaters.SerializerFormater;
import com.graphscape.commons.file.provider.resolver.TailRegionWidthResolver;
import com.graphscape.commons.lang.GsException;
import com.graphscape.commons.record.BiTreeSegmentI;
import com.graphscape.commons.record.IndexI;
import com.graphscape.commons.record.SerializerI;
import com.graphscape.commons.record.provider.index.rbtree.RedBlackTreeIndex.ColorEntry;
import com.graphscape.commons.record.provider.index.rbtree.formaters.ColorFormater;
import com.graphscape.commons.record.provider.segments.tree.DefaultBinaryTreeSegment;
import com.graphscape.commons.record.provider.serializer.IntSerializer;
import com.graphscape.commons.util.ByteArrayUtil;

/**
 * Red-Black tree <code>
 * +--------------------------------------------+
 * | key length	| key 	| value length	| value	|
 * |--------------------------------------------|
 * | 4 bytes	| *		| 4 bytes		| *		|
 * +--------------------------------------------+
 * </code>
 * 
 * @author wuzhen0808@gmail.com
 */
public class RedBlackTreeIndex<K, V> extends
		BiTreeIndexSupport<K, V, ColorEntry<K, V>> implements IndexI<K, V> {

	private static final String PROFILE_FIX_AFTER_ADDED = RedBlackTreeIndex.class
			.getName() + ".fixAfterAdded";
	private static final String PROFILE_WRITE_COLOR = RedBlackTreeIndex.class
			.getName() + ".writeColor";
	private static final Logger LOG = LoggerFactory
			.getLogger(RedBlackTreeIndex.class);

	public static final byte RED = 0;

	public static final byte BLACK = 1;

	public static final int ADDITIONALHEADERWIDTH = 1;//

	public static class ColorEntry<K, V> extends BiTreeIndexSupport.Entry<K, V> {
		RedBlackTreeIndex<K, V> index;

		/**
		 * @param pointer
		 * @param entryHeaderLength
		 * @param content
		 * @param segment
		 * @param tr
		 */
		@Override
		public void initialize(long pointer, Direct dir, int entryHeaderLength,
				BiTreeSegmentI segment, BiTreeSegmentProxySupport tr) {
			super.initialize(pointer, dir, entryHeaderLength, segment, tr);
			this.index = (RedBlackTreeIndex<K, V>) tr;
		}

		protected void writeColor(byte color) {
			index.beforeProfile(PROFILE_WRITE_COLOR);
			try {

				byte[] header = new byte[] { color };//
				this.writeUserHeader(header);
			} finally {
				index.afterProfile();
			}

		}

		protected boolean isRed() {
			return RED == color();
		}

		protected boolean isBlack() {
			return BLACK == color();
		}

		protected byte color() {

			return getUserHeader()[0];//
		}

		public boolean isBlackAndBothChildAreBlackIfHave() {
			if (this.isNill()) {
				return true;
			}
			if (this.isRed()) {
				return false;
			}
			return this.left(true).isBlack() && this.right(true).isBlack();
		}

		public ColorEntry<K, V> brother() {
			ColorEntry<K, V> parent = parent();
			ColorEntry<K, V> left = parent.left();

			if (left == null || this.pointer != left.pointer) {
				return left;
			}
			return parent.right();
		}

		public ColorEntry<K, V> uncle() {
			ColorEntry<K, V> parent = this.parent();
			return parent.brother();

		}

		public ColorEntry<K, V> left(boolean nullAsNill) {
			ColorEntry<K, V> rt = super.left();
			if (rt != null) {
				return rt;
			}
			if (nullAsNill) {
				rt = NillColorEntry.valueOf(this, Direct.LEFT);
			}
			return rt;

		}

		public ColorEntry<K, V> right(boolean nullAsNill) {
			ColorEntry<K, V> rt = super.right();
			if (rt != null) {
				return rt;
			}
			if (nullAsNill) {
				rt = NillColorEntry.valueOf(this, Direct.RIGHT);
			}
			return rt;
		}

		public boolean isNill() {
			return false;
		}

		@Override
		public String toString() {
			return this.getKey() + ","
					+ (this.color() == RED ? "Red" : "Black") + ","
					+ (this.direct);
		}

	}

	public static class NillColorEntry<K, V> extends ColorEntry<K, V> {
		ColorEntry<K, V> parent;

		public NillColorEntry() {

		}

		@Override
		public boolean isNill() {
			return true;
		}

		@Override
		protected byte color() {
			return BLACK;//
		}

		public static <K, V> NillColorEntry<K, V> valueOf(
				ColorEntry<K, V> parent, Direct dir) {
			NillColorEntry<K, V> rt = new NillColorEntry<K, V>();
			rt.initialize(parent, dir);//
			return rt;
		}

		public void initialize(ColorEntry<K, V> parent, Direct dir) {
			this.parent = parent;
			this.direct = dir;
		}

		@Override
		public ColorEntry<K, V> parent() {
			return this.parent;
		}

		@Override
		public ColorEntry<K, V> left() {
			throw new GsException("null pointer,not call this method");

		}

		@Override
		public ColorEntry<K, V> right() {
			throw new GsException("null pointer,not call this method");

		}

		@Override
		public Direct getDirect() {
			return this.direct;
		}

	}

	@Override
	protected ColorEntry<K, V> newEntry() {
		return new ColorEntry<K, V>();
	}

	private RedBlackTreeIndex(Comparator<K> comparator,
			SerializerI<K> marshaller, SerializerI<V> valueMarshaller,
			BiTreeSegmentI seg) {
		super(1, comparator, marshaller, valueMarshaller, seg);
	}

	public static <K, V> RedBlackTreeIndex<K, V> valueOf(String name,
			Comparator<K> comparator, SerializerI<K> keySer,
			SerializerI<V> valueSer, FileManagerI fm) {
		ByteWindowI window = new DefaultByteWindow("RB_NODE",
				new TailRegionWidthResolver())//
				.addChild(new DefaultByteWindow("RB", 1)//
						.formater(new ColorFormater()))//
				.addChild(new DefaultByteWindow("KLEN", 4)//
						.serializer(new IntSerializer())//
						.formater(new ConstantByteFormater(" ")))//
				.addChild(new DefaultByteWindow("KEY", "KLEN")//
						.serializer(keySer)//
						.formater(new SerializerFormater(12, true, "=")))//
				.addChild(new DefaultByteWindow("VLEN", 4)//
						.serializer(new IntSerializer())//
						.formater(new ConstantByteFormater("")))//
				.addChild(new DefaultByteWindow("VALUE", "VLEN")//
						.serializer(valueSer)//
						.formater(new SerializerFormater()))//
		;

		BiTreeSegmentI t = DefaultBinaryTreeSegment.valueOf(name,
				ADDITIONALHEADERWIDTH, window, fm);

		return new RedBlackTreeIndex<K, V>(comparator, keySer, valueSer, t);
	}

	@Override
	protected void afterRootAdded(ColorEntry<K, V> en) {
		// do nothing.
	}

	@Override
	protected void afterLeftAdded(ColorEntry<K, V> parent, ColorEntry<K, V> en) {
		this.fixAfterAdded(parent, true, en, true, parent, en);
	}

	@Override
	protected void afterRightAdded(ColorEntry<K, V> parent, ColorEntry<K, V> en) {
		this.fixAfterAdded(parent, false, en, false, parent, en);
	}

	protected void fixAfterAdded(ColorEntry<K, V> sourceParent,
			boolean sourceIsLeft, ColorEntry<K, V> sourceChild, boolean isLeft,
			ColorEntry<K, V> parent, ColorEntry<K, V> en) {

		if (LOG.isDebugEnabled()) {

			// this.segment.dump("before doFixAfterAdded,source(sourceParent:" +
			// sourceParent + ","
			// + (sourceIsLeft ? "L" : "R") + ",sourceChild:" + sourceChild +
			// "," + ")current("
			// + (isLeft ? "L" : "R") + ",child:" + en + ",parent:" + parent +
			// ")");
		}
		this.beforeProfile(PROFILE_FIX_AFTER_ADDED);
		try {

			this.doFixAfterAdded(sourceParent, sourceIsLeft, sourceChild,
					isLeft, parent, en);
		} finally {
			this.afterProfile();
		}
		if (LOG.isDebugEnabled()) {
			// this.segment.dump("before doFixAfterAdded,source(sourceParent:" +
			// sourceParent + ","
			// + (sourceIsLeft ? "L" : "R") + ",sourceChild:" + sourceChild +
			// "," + ")current("
			// + (isLeft ? "L" : "R") + ",child:" + en + ",parent:" + parent +
			// ")");
		}

	}

	protected void doFixAfterAdded(ColorEntry<K, V> sourceParent,
			boolean sourceIsLeft, ColorEntry<K, V> sourceChild, boolean isLeft,
			ColorEntry<K, V> parent, ColorEntry<K, V> en) {

		if (parent.color() == BLACK) {// en.color == RED,see
										// buildRight/LeftContent();
			return;// do nothing
		}
		// now,color of parent is RED
		// check color of uncle
		boolean isRight = !isLeft;
		ColorEntry<K, V> grand = parent.parent();
		ColorEntry<K, V> uncle = parent.brother();

		if (uncle == null || uncle.color() == BLACK) {

			// boolean isParentLeft = parent.isLeftOf(grand);
			boolean isParentLeft = parent.isLeft();// Of(grand);

			boolean isParentRight = !isParentLeft;
			if (isParentLeft) {
				if (isRight) {

					this.rotate(en, parent);
					ColorEntry<K, V> tmp = en;
					en = parent;
					parent = tmp;
				}

				/**
				 * <code>
				 * NOW: 
				 *         G(B)				  P(B)
				 *        /    \ 			 /    \
				 * 		 P(R)   U(B) 		C(R)   G(R)
				 * 		/  \				  	  /	 \
				 *     C(R) X			    	 X	 U(B)
				 *    /  \
				 * </code>
				 */

			}

			if (isParentRight) {
				if (isLeft) {

					this.rotate(en, parent);//
					ColorEntry<K, V> tmp = en;
					en = parent;
					parent = tmp;
				}
				// this.rotate(en, parent);
				/**
				 * <code>
				 * NOW: 
				 *     G(B)					 P(B) 		
				 *    /    \		  		/    \    
				 * 	 U(B)	P(R)		  G(R)   C(R)				  	 
				 * 		   / \			 /   \
				 *     	  X	 C(R) 	  U(B)    X
				 *       
				 *     			
				 * </code>
				 */
			}

			this.rotate(parent, grand);
			ColorEntry<K, V> tmp = parent;
			parent = grand;
			grand = tmp;//
			grand.writeColor(BLACK);
			parent.writeColor(RED);

		} else {// now,color of uncle is RED
			// now,set parent and uncle's colr both to BLACK.
			// set grand's color to RED.
			// recusively check the grand.
			/**
			 * <code>
			 *         G(B)					   G(R)
			 *        /    \				  /    \
			 * 		 P(R)   U(R) 	 		P(B)   U(B)
			 * 		/  \					/  \
			 *     C(R)					 C(R)
			 * </code>
			 */
			parent.writeColor(BLACK);
			uncle.writeColor(BLACK);

			ColorEntry<K, V> grandParent = grand.parent();
			if (grandParent == null) {
				// grand is root,it's black.
				return;
			}

			grand.writeColor(RED);
			isLeft = grand.isLeft();// Of(grandParent);
			this.fixAfterAdded(sourceParent, sourceIsLeft, sourceChild, isLeft,
					grandParent, grand);
		}
	}

	/**
	 * <code>
	 * Rotate right/left:
	 * Replace parent by child,replace every thing(the pointer,the color and key/value).
	 * 
	 * 
	 *         G   G		 	G   G
	 *        / \ /			    /\ /
	 * 	 	     P		    	  C	 
	 * 			/ \			     /  \
	 *     	   C   Y			     P
	 *        / \                   / \
	 *           X                 X   Y
	 * </code>
	 */
	protected ColorEntry<K, V> rotate(ColorEntry<K, V> child,
			ColorEntry<K, V> parent) {
		return this.rotate(child, parent, false);//
	}

	/**
	 * remain the pointer for entry and rebuild the link between child and
	 * parent.
	 * 
	 */
	protected ColorEntry<K, V> rotate(ColorEntry<K, V> child,
			ColorEntry<K, V> parent, boolean swapColor) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("rotate,child:" + child + ",parent:" + parent);
		}
		if (child.isNill()) {
			throw new IllegalArgumentException("rotate nill not supported,"
					+ parent);
		}
		boolean isLeft = child.isLeft();// Of(parent);

		long ppointer = this.removeParentLink(child.pointer);
		if (ppointer != parent.pointer) {
			// this.segment.dump("parent pointer error,child:" + child.pointer +
			// ",expected parent:"
			// + parent.pointer + ",but was:" + ppointer + ",");

			throw new GsException("parent pointer error,child:" + child.pointer
					+ ",expected parent:" + parent.pointer + ",but was:"
					+ ppointer + ",");
		}
		ColorEntry<K, V> grand = parent.parent();

		if (grand != null) {// grand point to child,child replace parent.
			if (parent.isLeft()) {// Of(grand)) {
				long oldLeftPoinger = this.updateLeftLink(grand.pointer,
						child.pointer);
				if (oldLeftPoinger != parent.pointer) {
					//
					// this.segment.dump("parent pointer error,expected:" +
					// parent.pointer + ",actural:"
					// + oldLeftPoinger);
					throw new GsException("parent pointer error,expected:"
							+ parent.pointer + ",actural:" + oldLeftPoinger);
				}
			} else {
				long oldRightPointer = this.updateRightLink(grand.pointer,
						child.pointer);
				if (oldRightPointer != parent.pointer) {
					// this.segment.dump("parent pointer error,expected:" +
					// parent.pointer + ",actural:"
					// + oldRightPointer);
					throw new GsException("parent pointer error,expected:"
							+ parent.pointer + ",actural:" + oldRightPointer);
				}
			}

		} else {// grand is null,child is the new root.

			this.setRootPointer(child.pointer);//

		}
		if (isLeft) {// left child,rotate to right,update the child's right
						// child with parent.
			// Parent -> X

			long rightPointer = this.updateRightLink(child.pointer,
					parent.pointer);

			if (rightPointer != -1) {
				this.updateLeftLink(parent.pointer, rightPointer);
			}
		} else {// right child, rotate to left
			long leftPointer = this.updateLeftLink(child.pointer,
					parent.pointer);

			if (leftPointer != -1) {
				this.updateRightLink(parent.pointer, leftPointer);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("after rotate,grand:"
					+ (grand == null ? null : grand.pointer));
		}

		if (swapColor) {
			this.swapColor(child, parent);//
		}

		child.direct = null;// unset direct.
		parent.direct = null;

		// this.print();
		return grand;
	}

	private void swapColor(ColorEntry<K, V> en1, ColorEntry<K, V> en2) {
		byte color1 = en1 == null ? BLACK : en1.color();// the new parent
		byte color2 = en2 == null ? BLACK : en2.color();// the new child
		if (en1 != null) {
			en1.writeColor(color2);
		}
		if (en2 != null) {
			en2.writeColor(color1);
		}
	}

	@Override
	protected void deleteEntry(ColorEntry<K, V> en) {

		this.deleteEntry(en, (ColorEntry<K, V>) en.left(true),
				(ColorEntry<K, V>) en.right(true));

	}

	protected void deleteEntry(ColorEntry<K, V> en, ColorEntry<K, V> left,
			ColorEntry<K, V> right) {
		// if (en.getKey().equals(new Integer(17))) {
		// System.out.println("17");//
		// }
		if (!left.isNill() && !right.isNill()) {
			// two child
			ColorEntry<K, V> next = right.leftMostChild();// this is the
															// successor

			this.swapContent(en, next);
			//
			en = getEntry(next.pointer);
			// point to the successor,and convert to one or no child issue
			//
			left = en.left(true);
			right = en.right(true);
			this.deleteEntry(en, left, right);// now it will be one/zero child.
			return;//
		}

		ColorEntry<K, V> parent = en.parent();
		boolean isBlackRemoved = en.color() == BLACK;

		// save the left/right

		Boolean isLeftRemoved = en.isLeft();// Of(parent);//

		ColorEntry<K, V> child = right.isNill() ? left : right;

		if (!child.isNill()) {
			// one child,remove from parent link.
			this.removeParentLink(child.pointer);
		}

		// do remove the node,child is removed,and now it have no child,so
		// remove from parent.
		this.removeParentLink(en.pointer);//
		this.remove(en.pointer);//
		// fix parent
		if (parent == null) {// update root pointer
			long pointer = child.isNill() ? NULL : child.pointer;
			this.setRootPointer(pointer);//
			if (!child.isNill()) {
				child.writeColor(BLACK);// ROOT should black?
			}
		} else {// has parent.
			if (!child.isNill()) {// has child.
				if (isLeftRemoved) {
					this.updateLeftLink(parent.pointer, child.pointer);

				} else {
					this.updateRightLink(parent.pointer, child.pointer);
				}
			}
		}
		// replace the direct.
		child.direct = en.direct;//

		// if (en.getKey().equals(new Integer(11))) {
		// System.out.println("11");//
		// }
		// if is Red,no need to fix.

		if (isBlackRemoved) {
			// parent tree removed one black,so fix it.

			this.fixAfterDeleted(child, parent);
		}

	}

	protected void fixAfterDeleted(ColorEntry<K, V> node,
			ColorEntry<K, V> parent) {
		// the new node is RED,just black it. balanced.

		if (RED == node.color()) {
			// case 0.1: node is RED,just update to BLACK.

			node.writeColor(BLACK);

			return;
		}

		// color is black,find the red continuely.

		if (parent == null) {// reach root node.the subtree is whole tree.just
								// write color of root.
			if (!node.isNill()) {
				node.writeColor(BLACK);
			}
			return;
		}
		boolean isLeft = node.isLeft();
		ColorEntry<K, V> brother = (ColorEntry<K, V>) (isLeft ? parent
				.right(true) : parent.left(true));
		ColorEntry<K, V> brotherLeft = (ColorEntry<K, V>) (brother.isNill() ? null
				: brother.left(true));
		ColorEntry<K, V> brotherRight = (ColorEntry<K, V>) (brother.isNill() ? null
				: brother.right(true));
		// case 1: focus to parent,by decrease the brother branch too.(RED
		// brother).
		if (parent.color() == BLACK && !brother.isNill()
				&& brother.isBlackAndBothChildAreBlackIfHave()) {

			brother.writeColor(RED);//

			ColorEntry<K, V> grand = parent.parent();
			// Boolean newIsLeft = grand == null ? null :
			// parent.isLeft();//Of(grand);

			// set brother to RED, and the parent tree is. balanced, fix
			// parent.move focus from N to P.
			this.fixAfterDeleted(parent, (ColorEntry<K, V>) parent.parent());
			return;
		}

		// case 2:focus to parent, by let the parent's color to RED.
		if (!brother.isNill() && brother.color() == RED) {// it's left/right is
															// black.
			// P,SL,SR:MUST be BLACK
			this.rotate(brother, parent, true);
			// now parent is RED
			this.fixAfterDeleted(node, parent);
			return;//
		}

		// case 2: P:RED,S,SL,SR:BLACK=> P:BLACK,S:RED,SL:SR:BLACK.balanced
		// over.
		if ((parent.color() == RED) && !brother.isNill()
				&& brother.isBlackAndBothChildAreBlackIfHave()) {
			// THIS is final condition.
			this.swapColor(parent, brother);// parent is black,one more
											// black.balanced.
			// not call more

			return;// OVER.
		}

		// case 2.1: if brother's both child is BLACK, it's safe to let
		// brother red and let the parent BLACK.
		// by doing so, the parent tree is balanced, focus move to parent
		// level.

		if (parent.color() == RED && !brother.isNill()
				&& brother.isBlackAndBothChildAreBlackIfHave()) {
			//
			this.swapColor(parent, brother);//
			// now parent is BLACK.
			parent = parent.parent();
			node = parent;

			isLeft = parent == null ? null : node.isLeft();// Of(parent);//

			this.fixAfterDeleted(node, parent);
			return;
		}

		// now, the SL or SR must be RED(or both are RED),it's final
		// condition(not call
		// fixXXX )

		boolean brotherLeftIsRed = (brotherLeft != null && brotherLeft.color() == RED);
		boolean brotherRightIsRed = (brotherRight != null && brotherRight
				.color() == RED);
		if (!brotherLeftIsRed && !brotherRightIsRed) {
			throw new GsException("bug,must be one red");
		}
		//
		// case 2.2:parent id RED, and one of brother's child is RED.There are
		// two RED in the parent tree, and its safe to
		// let one of them as BLACK.We fist rotate the brother, and let the
		// parent to be RED. Finally we paint parent to Black.
		// by doing so, it is resolved at the parent tree level.and no need
		// to fix on the parent.
		// but, before this, we must make sure the RED child of brother is
		// in the opposite side.
		if (isLeft) {
			// 2.2.1,perfect, rotate the brother
			if (brotherRightIsRed) {
				this.rotate(brother, parent, true);
				brotherRight.writeColor(BLACK);
				return;
			}
		} else {
			// 2.2.2,perfect, rotate the brother
			if (brotherLeftIsRed) {
				this.rotate(brother, parent, true);
				//
				brotherLeft.writeColor(BLACK);
				return;// FINISHED, opposite.
			}
		}
		// case 2.3:there are two RED in the parent tree, but cannot rotate
		// brother
		// at this moment,we must prepare.
		// rotate the RED one, and change the color with brother.
		// by doing so,we come into 2.2 case.

		if (isLeft) {
			if (brotherLeftIsRed) {
				this.rotate(brotherLeft, brother, true);

				brotherRight = brother;
				brother = brotherLeft;

			}
			// now the brother right is red
			this.rotate(brother, parent, true);
			brotherRight.writeColor(BLACK);// change one RED to BLACK.

		} else {// right
			if (brotherRightIsRed) {
				this.rotate(brotherRight, brother, true);

				brotherLeft = brother;
				brother = brotherRight;
			}
			// brotherLeft is RED
			this.rotate(brother, parent, true);
			brotherLeft.writeColor(BLACK);// change one RED to BLACK
		}

	}

	// swap the content between two entries,remain the pointer and structure.
	// preserve the color,swap the content
	protected void swapContent(ColorEntry<K, V> e1, ColorEntry<K, V> e2) {

		if (e1 == null || e2 == null) {
			throw new IllegalArgumentException("entry is null,e1:" + e1
					+ ",e2:" + e2);
		}

		byte[] content1 = e2.getTheContent();
		byte[] content2 = e1.getTheContent();

		long p1 = this.updateContent(e1.pointer, content1);
		if (p1 != e1.pointer) {
			throw new GsException("should not update pointer");
		}

		long p2 = this.updateContent(e2.pointer, content2);
		if (p2 != e2.pointer) {
			throw new GsException("should not update pointer");
		}
		Direct dir1 = e1.direct;
		Direct dir2 = e2.direct;
		e1.direct = dir2;
		e2.direct = dir1;

	}

	@Override
	protected byte[] buildLeftHeader() {

		return new byte[] { RED };
	}

	@Override
	protected byte[] buildRightHeader() {
		return new byte[] { RED };
	}

	@Override
	protected byte[] buildRootHeader() {
		return new byte[] { BLACK };
	}

}
