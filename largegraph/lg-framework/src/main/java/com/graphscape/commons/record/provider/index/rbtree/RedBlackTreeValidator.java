/**
 * Jan 18, 2014
 */
package com.graphscape.commons.record.provider.index.rbtree;

import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.record.provider.index.rbtree.RedBlackTreeIndex.ColorEntry;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class RedBlackTreeValidator {

	public RedBlackTreeValidator() {

	}

	public <K, V> ErrorInfos validate(RedBlackTreeIndex<K, V> idx) {
		ErrorInfos rt = new ErrorInfos();
		this.validate(idx, rt);
		return rt;
	}

	public <K, V> void validate(RedBlackTreeIndex<K, V> idx, ErrorInfos eis) {
		ColorEntry<K, V> en = idx.getRoot();
		if (en == null) {
			return;
		}
		this.validateColor(en, idx, eis);
		if (eis.hasError()) {
			return;
		}
		this.validateBlackDepth(en, idx, eis);

	}

	public <K, V> int validateBlackDepth(ColorEntry<K, V> en, RedBlackTreeIndex<K, V> idx, ErrorInfos eis) {
		if (en == null) {// null is black.
			return 1;
		}
		
		ColorEntry<K, V> left = en.left();

		ColorEntry<K, V> right = en.right();
		int leftD = this.validateBlackDepth(left, idx, eis);
		int rightD = this.validateBlackDepth(right, idx, eis);

		int delta = 0;// for RED

		if (leftD != rightD) {
			eis.add("" + en.getKey() + "'s leftBlacks:" + leftD + "<>" + "rightBlackDepth" + rightD);
		
		}
		
		if (en.isBlack()) {
			delta = 1;// FOR BLACK.
		}
		return leftD + delta;

	}

	public <K, V> void validateColor(ColorEntry<K, V> en, RedBlackTreeIndex<K, V> idx, ErrorInfos eis) {
		if(en == null){
			return;
		}
		ColorEntry<K, V> left = en.left();
		ColorEntry<K, V> right = en.right();
		if (en!=null && en.isRed()) {

			if (left!=null&&left.isRed()) {
				eis.add(en.getKey() + " and left:" + left.getKey() + " are all red");
				return;
			}

			if (right!=null&&right.isRed()) {
				eis.add(en.getKey() + " and right:" + right.getKey() + " are all red");
				return;
			}

		}
		this.validateColor(left, idx, eis);
		this.validateColor(right, idx, eis);

	}

}
