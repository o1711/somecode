/**
 * Jan 7, 2014
 */
package com.graphscape.commons.util;

import com.graphscape.commons.lang.GsException;

/**
 * @author wuzhen0808@gmail.com
 * 
 */
public class ByteArrayUtil {

	public static byte[] writeLong(long v) {
		byte[] rt = new byte[8];
		writeLong(v, rt, 0);
		return rt;
	}

	public static byte[] writeInt(int v) {
		byte[] rt = new byte[4];
		writeInt(v, rt, 0);
		return rt;
	}

	public static byte[] writeShort(short v) {
		byte[] rt = new byte[2];
		writeShort(v, rt, 0);
		return rt;
	}

	public static void writeShort(int v, byte[] writeBuffer, int offset) {

		writeBuffer[0 + offset] = (byte) (v >> 8);
		writeBuffer[1 + offset] = (byte) (v >> 0);
	}

	public static void writeInt(int v, byte[] writeBuffer, int offset) {
		writeBuffer[0 + offset] = (byte) (v >> 24);
		writeBuffer[1 + offset] = (byte) (v >> 16);
		writeBuffer[2 + offset] = (byte) (v >> 8);
		writeBuffer[3 + offset] = (byte) (v >> 0);
	}

	public static void writeLong(long v, byte[] writeBuffer, int offset) {
		writeBuffer[0 + offset] = (byte) (v >> 56);
		writeBuffer[1 + offset] = (byte) (v >> 48);
		writeBuffer[2 + offset] = (byte) (v >> 40);
		writeBuffer[3 + offset] = (byte) (v >> 32);
		writeBuffer[4 + offset] = (byte) (v >> 24);
		writeBuffer[5 + offset] = (byte) (v >> 16);
		writeBuffer[6 + offset] = (byte) (v >> 8);
		writeBuffer[7 + offset] = (byte) (v >> 0);

	}

	public static int readInt(byte[] buf, int offset) {
		return ((buf[0 + offset] << 24)//
				| ((buf[1 + offset] & 0xff) << 16)//
				| ((buf[2 + offset] & 0xff) << 8) //
		| ((buf[3 + offset] & 0xff) << 0));

	}

	@Deprecated
	public static int readInt(int offset, byte[] buf) {

		return ((buf[0 + offset] << 24)//
				| ((buf[1 + offset] & 0xff) << 16)//
				| ((buf[2 + offset] & 0xff) << 8) //
		| ((buf[3 + offset] & 0xff) << 0));

	}

	public static short readShort(int offset, byte[] buf) {

		return (short) ((buf[0 + offset] << 8) | (buf[1 + offset] & 0xff));
	}

	public static long readLong(byte[] buf, int offset) {
		return (((long) buf[0 + offset] << 56) | //
				((long) (buf[1 + offset] & 0xff) << 48) | //
				((long) (buf[2 + offset] & 0xff) << 40) | //
				((long) (buf[3 + offset] & 0xff) << 32) | //
				((long) (buf[4 + offset] & 0xff) << 24) | //
				((long) (buf[5 + offset] & 0xff) << 16) | //
				((long) (buf[6 + offset] & 0xff) << 8) | //
		((long) (buf[7 + offset] & 255) << 0));
	}

	public static long readLong(int offset, byte[] buf) {

		return (((long) buf[0 + offset] << 56) | //
				((long) (buf[1 + offset] & 0xff) << 48) | //
				((long) (buf[2 + offset] & 0xff) << 40) | //
				((long) (buf[3 + offset] & 0xff) << 32) | //
				((long) (buf[4 + offset] & 0xff) << 24) | //
				((long) (buf[5 + offset] & 0xff) << 16) | //
				((long) (buf[6 + offset] & 0xff) << 8) | //
		((long) (buf[7 + offset] & 255) << 0));
	}

	public static int readInt(byte[] buf) {
		return readInt(0, buf);
	}

	public static long readLong(byte[] buf) {
		return readLong(0, buf);
	}

	public static short readShort(byte[] buf) {
		return readShort(0, buf);
	}

	public static Number readNumber(byte[] buf) {
		if (buf.length == 2) {
			return readShort(0, buf);
		} else if (buf.length == 4) {
			return readInt(0, buf);
		} else if (buf.length == 8) {
			return readLong(0, buf);
		}
		throw new GsException("length error:" + buf.length);
	}

	public static long[] readLongArray(byte[] buffer) {
		int size = buffer.length / 8;
		long[] rt = new long[size];
		for (int i = 0; i < size; i++) {
			rt[i] = readLong(i * 8, buffer);
		}
		return rt;
	}

	public static void write(byte[] src, byte[] dest) {
		write(src, 0, dest, 0, src.length);
	}

	public static void write(byte[] source, int srcPos, byte[] dest,
			int destPos, int length) {
		System.arraycopy(source, srcPos, dest, destPos, length);
	}

	public static byte[] concate(long ls, byte[]... bs) {
		return concate(writeLong(ls), concate(bs));
	}

	public static byte[] concate(byte[]... bs) {
		int size = 0;
		for (int i = 0; i < bs.length; i++) {
			size += bs[i].length;
		}
		byte[] rt = new byte[size];
		int offset = 0;
		for (int i = 0; i < bs.length; i++) {
			System.arraycopy(bs[i], 0, rt, offset, bs[i].length);
			offset += bs[i].length;
		}
		return rt;

	}

	public static byte[] subArray(byte[] b, int offset) {
		return subArray(b, offset, b.length - offset);
	}

	public static byte[] subArray(byte[] b, int offset, int len) {
		if (len < 0) {
			throw new IllegalArgumentException("len neg:" + len);
		}
		byte[] rt = new byte[len];
		System.arraycopy(b, offset, rt, 0, len);
		;
		return rt;
	}

	public static boolean isEquals(byte[] k, byte[] key) {
		if (k.length != key.length) {
			return false;
		}
		for (int i = 0; i < k.length; i++) {
			if (k[i] != key[i]) {
				return false;
			}
		}
		return true;
	}

	public static void assertEquals(byte[] b1, byte[] b2) {
		if (isEquals(b1, b2)) {
			return;
		}
		throw new GsException("expected:" + hex(b1) + ",actural:" + hex(b2));
	}

	public static String hex(byte[] value) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			// o/x
			sb.append(String.format("%02x", value[i] & 0xff));
		}
		return sb.toString();
	}

}
