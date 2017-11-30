/**
 * Jan 12, 2014
 */
package com.graphscape.commons.file;

import java.util.List;

import com.graphscape.commons.lang.ErrorInfos;
import com.graphscape.commons.record.SerializerI;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public interface ByteWindowI {

	public String getName();

	public ByteWindowI addChild(ByteWindowI child);

	public boolean isFirstChild();

	public boolean isLastChild();

	public ByteWindowI getFirstChild();

	public ByteWindowI getLastChild();

	public List<ByteWindowI> getChildList();

	public ByteWindowI getLeft();

	public ByteWindowI getRight();

	public ByteWindowI getParent();

	public ByteWindowI getRoot();

	public long getWidth(ByteCursorI bf);

	public long getOccurs(ByteCursorI bf);

	public ByteWindowI getChild(String windowName, boolean b);

	/**
	 * open with a new memory region.
	 * 
	 * @return
	 */
	public ByteCursorI open();

	public ByteCursorI open(PlainRegionI region);

	public ByteCursorI open(PlainRegionI region, long offset);

	public ByteCursorI open(byte[] content);

	public ByteCursorI open(byte[] content, int offset);

	public void setParent(ByteWindowI parent);

	public <T> ByteWindowI serializer(SerializerI<T> ser);

	public <T> SerializerI<T> getSerializer();

	public boolean isRoot();

	public ByteWindowI formater(LeafByteFormaterI formater);

	public LeafByteFormaterI getFormater();

	public ErrorInfos validate(PlainRegionI region, long offset);

}
