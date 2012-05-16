package mayaya.util.tool;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	private final static char[] hexdigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	private final static MessageDigest md5 = createMessageDigestInstance("md5");
	
	public static byte[] encode(String src) {
		return encode(src, "gbk");
	}
	
	public static byte[] encode(String src, String charset) {
		try {
			return ((MessageDigest)(md5.clone())).digest(src.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BigInteger toBigintResult(String src) {
		byte[] r = encode(src);
		return new BigInteger(1, new byte[]{r[7], r[6], r[5], r[4], r[3], r[2], r[1], r[0]});
	}
	
	public static String to64BitStrResult(String src) {
		return toBigintResult(src).toString();
	}
	
	public static String to64BitHexStrResult(String src) {
		return toBigintResult(src).toString(16);
	}
	
	private static long toLongResult(String src) {
		byte[] r = encode(src);
        return (((long)r[7] << 56) +
                ((long)(r[6] & 255) << 48) +
                ((long)(r[5] & 255) << 40) +
                ((long)(r[4] & 255) << 32) +
                ((long)(r[3] & 255) << 24) +
                ((r[2] & 255) << 16) +
                ((r[1] & 255) <<  8) +
                ((r[0] & 255) <<  0));
	}
	
	public static String to128BitHexStrResult(String src) {
		byte[] r = encode(src);
		int l = r.length;
		char str[] = new char[l * 2];
		
		int k = 0;
		byte b;
		for(int i=0; i<l; i++) {
			b = r[i];
			str[k++] = hexdigits[b >>> 4 & 0x0f];
			str[k++] = hexdigits[b & 0x0f];
		}

		return new String(str);
	}
	
	public static String to128BitHexStrResult(byte[] r) {
		int l = r.length;
		char str[] = new char[l * 2];
		
		int k = 0;
		byte b;
		for(int i=0; i<l; i++) {
			b = r[i];
			str[k++] = hexdigits[b >>> 4 & 0x0f];
			str[k++] = hexdigits[b & 0x0f];
		}

		return new String(str);
	}
	
	public static String encode(Object... params) {
		StringBuffer buf = new StringBuffer();
		for(Object p : params) buf.append(p);
		return to128BitHexStrResult(buf.toString());
	}
	
	public static MessageDigest createMessageDigestInstance(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("NoSuchAlgorithm", e);
		}
	}
	
	public static String encodeFile(String fullFilePathName) {
		try {
			final int MaxBufferLength = 1*1024*1024;
			byte[] readBuffer = new byte[MaxBufferLength];
			MessageDigest md = ((MessageDigest)(md5.clone()));
			DigestInputStream in = new DigestInputStream(new FileInputStream(fullFilePathName), md);
			while (in.read(readBuffer, 0, MaxBufferLength) != -1);

			//BigInteger bigInt = new BigInteger(1, md.digest());
			//return bigInt.toString(16);
			return to128BitHexStrResult(md.digest());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		test("中国");
	}
	
	private static void test(String str) {
		System.out.println(MD5.to64BitHexStrResult(str));
		System.out.println(MD5.toLongResult(str));
		System.out.println(Long.toHexString(MD5.toLongResult(str)));
		System.out.println(MD5.toBigintResult(str));
		System.out.println(MD5.to64BitStrResult(str));
		System.out.println(MD5.toBigintResult(str).longValue());
		System.out.println(new BigInteger("13499386912920701135"));
	}
}
