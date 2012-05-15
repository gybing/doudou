package doudou.util.tool;

/**
 * <p>
 * Description: MD5的算法在RFC1321 中定义
 * </p>
 * 
 * @author chaogao
 */

public class MD5 {
	
	public static String getMD5(String input) {
		String s = null;
		byte[] source = input.getBytes(); 
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd','e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			//MD5 的计算结果是一个 128 位的长整数，用字节表示就是 16 个字节
			byte tmp[] = md.digest();
			//每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符
			char str[] = new char[16 * 2]; 
	
			int k = 0; 
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				// 取字节中高 4 位的数字转换,>>>为逻辑右移，将符号位一起右移,取字节中低 4 位的数字转换
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
															
															
				str[k++] = hexDigits[byte0 & 0xf]; 
			}
			s = new String(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
