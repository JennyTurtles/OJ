package edu.dhu.ws;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

public class Encrypt {
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * DES算法，加密
	 * 
	 * @param data
	 *            待加密字符串
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return 加密后的字节数组，一般结合Base64编码使用
	 * @throws InvalidAlgorithmParameterException
	 * @throws Exception
	 */
	public static byte[] encrypt(String key, String data) {
		if (data == null)
			return new byte[0];
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			byte[] bytes = cipher.doFinal(data.getBytes());
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	/**
	 * DES算法，解密
	 * 
	 * @param data
	 *            待解密字符串
	 * @param key
	 *            解密私钥，长度不能够小于8位
	 * @return 解密后的字节数组
	 * @throws Exception
	 *             异常
	 */
	public static String decrypt(String key, byte[] data) {
		if (data.length == 0)
			return "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><respMsg>用户名或密码错误</respMsg></root>";
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return new String(cipher.doFinal(data));
		} catch (Exception e) {
			e.printStackTrace();
			return "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?><root><respMsg>用户名或密码错误</respMsg></root>";
		}
	}

	/**
	 * 加密工具类 md5加密出来的长度是32位 sha加密出来的长度是40位
	 */
	/*
	 * public class Encrypt {
	 *//**
	 * 测试
	 * 
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { // md5加密测试 String md5_1 =
	 * md5("123456"); String md5_2 = md5("测试"); // System.out.println(md5_1 +
	 * "\n" + md5_2); // sha加密测试 String sha_1 = sha("123456"); String sha_2 =
	 * sha("测试"); // System.out.println(sha_1 + "\n" + sha_2);
	 * 
	 * }
	 *//**
	 * 加密
	 * 
	 * @param inputText
	 * @return
	 */
	/*
	 * public static String e(String inputText) { return md5(inputText); }
	 *//**
	 * 二次加密
	 * 
	 * @param inputText
	 * @return
	 */
	/*
	 * public static String md5AndSha(String inputText) { return
	 * sha(md5(inputText)); }
	 *//**
	 * md5加密
	 * 
	 * @param inputText
	 * @return
	 */
	/*
	 * public static String md5(String inputText) { return encrypt(inputText,
	 * "md5"); }
	 *//**
	 * sha加密
	 * 
	 * @param inputText
	 * @return
	 */
	/*
	 * public static String sha(String inputText) { return encrypt(inputText,
	 * "sha-1"); }
	 *//**
	 * md5或者sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	/*
	 * private static String encrypt(String inputText, String algorithmName) {
	 * if (inputText == null || "".equals(inputText.trim())) { throw new
	 * IllegalArgumentException("请输入要加密的内容"); } if (algorithmName == null ||
	 * "".equals(algorithmName.trim())) { algorithmName = "md5"; } String
	 * encryptText = null; try { MessageDigest m =
	 * MessageDigest.getInstance(algorithmName);
	 * m.update(inputText.getBytes("UTF8")); byte s[] = m.digest(); //
	 * m.digest(inputText.getBytes("UTF8")); return hex(s); } catch
	 * (NoSuchAlgorithmException e) { e.printStackTrace(); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } return
	 * encryptText; }
	 *//**
	 * 返回十六进制字符串
	 * 
	 * @param arr
	 * @return
	 */
	/*
	 * private static String hex(byte[] arr) { StringBuffer sb = new
	 * StringBuffer(); for (int i = 0; i < arr.length; ++i) {
	 * sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
	 * } return sb.toString(); }
	 */

}
