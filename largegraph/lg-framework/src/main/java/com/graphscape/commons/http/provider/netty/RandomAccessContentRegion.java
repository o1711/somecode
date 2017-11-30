/**
 * Dec 12, 2013
 */
package com.graphscape.commons.http.provider.netty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

import org.apache.commons.vfs2.RandomAccessContent;
import org.jboss.netty.channel.FileRegion;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class RandomAccessContentRegion implements FileRegion {

	private RandomAccessContent rac;

	private long position;
	private long count;

	public RandomAccessContentRegion(RandomAccessContent rac, long position, long count) {
		this.rac = rac;
		this.position = position;
		this.count = count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.util.ExternalResourceReleasable#releaseExternalResources
	 * ()
	 */
	@Override
	public void releaseExternalResources() {
		try {
			rac.close();
		} catch (IOException e) {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.FileRegion#getPosition()
	 */
	@Override
	public long getPosition() {

		return this.position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.FileRegion#getCount()
	 */
	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return this.count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.FileRegion#transferTo(java.nio.channels.
	 * WritableByteChannel, long)
	 */
	@Override
	public long transferTo(WritableByteChannel target, long position)
			throws IOException {
		long pos = this.position + position;
		this.rac.seek(pos);
		int len = (int) (this.count - pos);
		if (len > 8192) {
			len = 8192;
		}
		byte[] bs = new byte[len];
		this.rac.readFully(bs);

		ByteBuffer bf = ByteBuffer.wrap(bs);
		target.write(bf);
		return len;
	}

}
