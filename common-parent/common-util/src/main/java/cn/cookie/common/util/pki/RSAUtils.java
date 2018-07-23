package cn.cookie.common.util.pki;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * <p>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * <p>
 * plain:{"age":18, "name": "zhangsan"}
 * encrypt by public key:HKlpDW0lNQ0DQRUf0BuXxeT0x8VMnzRvUg5pQNEFyflkKXlGeN/NcRjFs8mpaAtmZ9rO5wovl3aP9YmuPxLOQXaPMGMo2jkdF0EQIZRHtT2ihA0iyDHy7+oC2rTaDvB7J2Kr+ZJ8BQVsDT+B4tppgylHs2L07Sfk5cT6n9fBqq8=
 * encrypt by private key:Snjvlc9pwAr/FeifmI5yts1wA8pvZciF3xkjVBn36VcyonSflMWtFpypLN6YN45fnL+yOKn0ulOxJb9LQXhJdX1cukEgoOlMpXRZZw9a1CZPEdhDi1XsIfvezNyht2/nsNYPGa0fL55+9pYPBmwgT2XN59p2Jw3PdrUm/+Drjqg=
 *
 * </p>
 */
public class RSAUtils {
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	private static final int MAX_ENCRYPT_BLOCK = 117;

	private static final int MAX_DECRYPT_BLOCK = 128;

	public static void main(String... strings) throws Exception {
		String file = "/root/d/pki/dev/api.upenny.cn/rsa_key_paire_pkcs8.pem";
		String pubfile = "/root/d/pki/dev/api.upenny.cn/rsa_public_key.pem";

		// System.out.println(new String(s));
		final PrivateKey privateKey = loadPrivateKey(file);
		System.out.println(privateKey);

		final PublicKey publicKey = loadPublicKey(pubfile);
		System.out.println(publicKey);

		final String data = "{\"age\":18, \"name\": \"zhangsan\"}";
		System.out.println("-------------source:" + data);
		long start = System.currentTimeMillis();
		String enc = encryptByPublicKey(data, publicKey, "utf-8");
		System.out.println("-------------enc:" + enc);
		
		String back = decryptByPrivateKey(enc, privateKey, "utf-8");
		System.out.println("back:" + back);

		String enc2 = encryptByPrivateKey(data, privateKey, "utf-8");
		System.out.println("-------------enc2:" + enc2);
		
		String back2 = decryptByPublicKey(enc2, publicKey, "utf-8");
		System.out.println("back2:" + back2);

		
		long stop = System.currentTimeMillis();
		System.out.println("-----------done:" + (stop - start) / 1000);
	}

	/**
	 * method will close inputSteam
	 * @param pemFile
	 * @return
	 */
	public static PublicKey loadPublicKey(InputStream pemFileInputStream) {
		return readPublicKey(readPEMFile(pemFileInputStream));
	}

	/**
	 * method will close inputSteam
	 * @param pkcs8PemFile
	 * @return
	 */
	public static PrivateKey loadPrivateKey(InputStream pkcs8PemFileInputStream) {
		return readPrivateKey(readPEMFile(pkcs8PemFileInputStream));
	}
	
	/**
	 * 
	 * @param pemFile
	 * @return
	 */
	public static PublicKey loadPublicKey(String pemFile) {
		return readPublicKey(readPEMFile(pemFile));
	}

	/**
	 * 
	 * @param pkcs8PemFile
	 * @return
	 */
	public static PrivateKey loadPrivateKey(String pkcs8PemFile) {
		return readPrivateKey(readPEMFile(pkcs8PemFile));
	}

	/**
	 * read pem file, delete first and last line, sth. like:<br />
	 * <p>
	 * -----BEGIN PUBLIC KEY----- -----END PUBLIC KEY-----
	 * </p>
	 * 
	 * @param filename
	 * @return
	 */
	public static String readPEMFile(String filename) {
		try {
			return readPEMFile(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * method will close inputSteam
	 * @param stream pem file inputstream
	 * @return
	 */
	public static String readPEMFile(InputStream stream) {
		if (null != stream) {
			BufferedReader in = null;
			StringBuilder ret = new StringBuilder();
			String line;
			try {
				in = new BufferedReader(new InputStreamReader(stream, "ASCII"));
				line = in.readLine();
				while (null != line) {
					if (!(line.startsWith("-----BEGIN ") || line.startsWith("-----END "))) {
						ret.append(line);
						ret.append("\n");
					}

					line = in.readLine();
				}

				return ret.toString();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				try {
					stream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				if (null != in) {
					try {
						in.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param pkcs8Base64String
	 *            <p>
	 *            delete the first and last line, sth. like below: 
	 *				-----BEGIN PRIVATE KEY-----
					-----END PRIVATE KEY-----
	 *            </p>
	 * @return
	 */
	public static PrivateKey readPrivateKey(String pkcs8Base64String) {
		byte[] keyByte = Base64Utils.decode(pkcs8Base64String);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyByte);
			RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

			return privateKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static PublicKey readPublicKey(String pkcs8Base64String) {
		byte[] keyByte = Base64Utils.decode(pkcs8Base64String);
		try {
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyByte);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PublicKey publicKey = (PublicKey) keyFactory.generatePublic(x509KeySpec);

			return publicKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 * 
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 * @return
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, PrivateKey privateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			return cipher.doFinal(encryptedData);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 公钥加密
	 * 
	 * @param content
	 *            待加密内容
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            content的字符集，如UTF-8, GBK, GB2312
	 * @return 密文内容 base64 ASCII
	 */
	public static String encryptByPublicKey(String content, PublicKey publicKey, String charset) {
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] data = (charset == null || charset.isEmpty()) ? content.getBytes() : content.getBytes(charset);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}

			byte[] encryptedData = Base64Utils.encode(out.toByteArray());
			out.close();

			return new String(encryptedData, "ASCII");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 私钥加密
	 * 
	 * @param content
	 *            待加密内容
	 * @param privateKey
	 *            私钥
	 * @param charset
	 *            content的字符集，如UTF-8, GBK, GB2312
	 * @return 密文内容 base64 ASCII
	 */
	public static String encryptByPrivateKey(String content, PrivateKey privateKey, String charset) {
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] data = (charset == null || charset.isEmpty()) ? content.getBytes() : content.getBytes(charset);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}

			byte[] encryptedData = Base64Utils.encode(out.toByteArray());
			out.close();

			return new String(encryptedData, "ASCII");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 私钥解密
     * 
     * @param content    待解密内容(base64, ASCII)
     * @param privateKey 私钥
     * @param charset    加密前字符的字符集，如UTF-8, GBK, GB2312
     * @return 明文内容
	 * @return
	 */
	public static String decryptByPrivateKey(String content, PrivateKey privateKey, String charset)  {
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] encryptedData = Base64Utils.decode(content);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();

			return (charset == null || charset.isEmpty()) ? new String(decryptedData) : new String(decryptedData, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 公钥解密
     * 
     * @param content    待解密内容(base64, ASCII)
     * @param publicKey 公钥
     * @param charset    加密前字符的字符集，如UTF-8, GBK, GB2312
     * @return 明文内容
	 * @return
	 */
	public static String decryptByPublicKey(String content, PublicKey publicKey, String charset)  {
		try {
			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] encryptedData = Base64Utils.decode(content);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();

			return (charset == null || charset.isEmpty()) ? new String(decryptedData) : new String(decryptedData, charset);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
