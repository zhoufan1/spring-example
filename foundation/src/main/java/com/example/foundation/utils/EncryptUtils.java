package com.example.foundation.utils;

import com.google.common.base.Splitter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.stream.Collectors;

public final class EncryptUtils {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String SALT = "2_~!@==#$++%^&*(121)";
    private static MessageDigest SHA1;
    private static MessageDigest MD5;
    private final static Charset UTF8 = StandardCharsets.UTF_8;

    static {
        try {
            SHA1 = MessageDigest.getInstance("sha1");
            MD5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException("no md5 algorithm error...", e);
        }
    }

    /**
     * 混淆加密
     */
    public static String cryptogram(String val) {
        if (StringUtils.isBlank(val)) {
            return val;
        }
        return toHex(SHA1.digest((val + SALT).getBytes(UTF8)));
    }

    /**
     * SHA1 加密
     **/
    public static String sha1(String val) {
        if (StringUtils.isBlank(val)) {
            return val;
        }
        return toHex(SHA1.digest(val.getBytes()));
    }

    /**
     * MD5 加密
     **/
    public static String md5(String val) {
        if (StringUtils.isBlank(val)) {
            return val;
        }
        return toHex(MD5.digest(val.getBytes())).toLowerCase();
    }

    /**
     * MD5 加密
     **/
    public static String md5(File file) {
        FileInputStream fis = null;
        DigestInputStream dis = null;
        try {
            fis = new FileInputStream(file);
            dis = new DigestInputStream(fis, MD5);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (dis.read(buffer) <= 0) break;
            };
            return toHex(dis.getMessageDigest().digest()).toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException("");
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fis);
        }
    }

    /**
     * 转 16 进制
     **/
    public static String toHex(String val) {
        if (StringUtils.isBlank(val)) {
            return val;
        }
        return new String(toHex(val.getBytes(UTF8)));
    }

    /**
     * 字符分隔，如果有中文则按汉字分隔
     */
    public static List<String> splitEachWord(String src) {
        return Splitter.on("\\u").splitToList(utf8ToUnicode(src)).stream()
                .filter(s -> !StringUtils.isBlank(s))
                .map(s -> unicodeToUtf8("\\u" + s)).collect(Collectors.toList());
    }

    /**
     * utf-8 转 unicode
     */
    public static String utf8ToUnicode(String src) {
        char[] myBuffer = src.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < src.length(); i++) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(myBuffer[i]);
            if (ub == Character.UnicodeBlock.BASIC_LATIN) {
                sb.append(myBuffer[i]);
            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                int j = (int) myBuffer[i] - 65248;
                sb.append((char) j);
            } else {
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * unicode 转 utf-8
     */
    public static String unicodeToUtf8(String src) {
        char aChar;
        int len = src.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = src.charAt(x++);
            if (aChar == '\\') {
                aChar = src.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = src.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    switch (aChar) {
                        case 't':
                            aChar = '\t';
                            break;
                        case 'r':
                            aChar = '\r';
                            break;
                        case 'n':
                            aChar = '\n';
                            break;
                        case 'f':
                            aChar = '\f';
                            break;
                        default:
                            break;
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }


    private static String toHex(byte[] bytes) {
        char[] rs = new char[bytes.length * 2];
        for (int i = 0; i < rs.length; i = i + 2) {
            byte b = bytes[i / 2];
            rs[i] = HEX_DIGITS[(b >>> 0x4) & 0xf];
            rs[i + 1] = HEX_DIGITS[b & 0xf];
        }
        return new String(rs);
    }
}