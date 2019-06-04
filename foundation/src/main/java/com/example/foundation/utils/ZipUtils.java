/**
 *  www.yupaopao.cn 2014-2017©All Rights Reserved
 */
package com.example.foundation.utils;

import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * GZIP 压缩 解压 ZIP 压缩 解压
 * </p>
 *
 * @author xiaoyu.wang
 * @date 2017年8月2日 上午10:34:41
 * @version
 */
@Slf4j
@SuppressWarnings("restriction")
public class ZipUtils {

	/**
	 * <p>
	 * GZIP压缩
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public static String gzip(String value) {
		if (value == null || value.length() == 0) {
			return value;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(value.getBytes());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (gzip != null) {
				try {
					gzip.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 * <p>
	 * GZIP解压
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public static String ungzip(String value) {
		if (value == null || value.length() == 0) {
			return value;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = null;
		GZIPInputStream ginzip = null;
		byte[] compressed = null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder().decodeBuffer(value);
			in = new ByteArrayInputStream(compressed);
			ginzip = new GZIPInputStream(in);
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return decompressed;
	}

	/**
	 * <p>
	 * ZIP压缩
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public static final String zip(String value) {
		if (value == null || value.length() == 0) {
			return value;
		}
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(value.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
		} catch (IOException e) {
			compressed = null;
			log.error(e.getMessage(), e);
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return compressedStr;
	}

	/**
	 * <p>
	 * ZIP解压
	 * </p>
	 * 
	 * @param value
	 * @return
	 */
	public static final String unzip(String value) {
		if (value == null || value.length() == 0) {
			return value;
		}
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
			byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(value);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
			log.error(e.getMessage(), e);
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return decompressed;
	}
}
