package cn.cookie.common.util;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES
{

    public DES(String desKey)
    {
        this.desKey = desKey.getBytes();
    }

    public byte[] desEncrypt(byte plainText[])
        throws Exception
    {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        javax.crypto.SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(1, key, sr);
        byte data[] = plainText;
        byte encryptedData[] = cipher.doFinal(data);
        return encryptedData;
    }

    public byte[] desDecrypt(byte encryptText[])
        throws Exception
    {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        javax.crypto.SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(2, key, sr);
        byte encryptedData[] = encryptText;
        byte decryptedData[] = cipher.doFinal(encryptedData);
        return decryptedData;
    }

    public String encrypt(String input)
        throws Exception
    {
        return new String(Base64.encode(desEncrypt(input.getBytes("UTF-8"))));
    }

    public String decrypt(String input)
        throws Exception
    {
        byte result[] = Base64.decode(input);
        return new String(desDecrypt(result), "UTF-8");
    }

    public String encrypt2(String input)
        throws Exception
    {
        return new String(desEncrypt(input.getBytes("UTF-8")),"UTF-8");
    }

    public String decrypt2(String input)
        throws Exception
    {
        return new String(desDecrypt(input.getBytes("UTF-8")), "UTF-8");
    }

    public static void main(String args[])
        throws Exception
    {
        String key = "shanghaiqianlongjinrong";
        String str="钱才";
        DES crypt = new DES(key);
        String encryptStr = crypt.encrypt(str);
        System.out.println((new StringBuilder("Encode:")).append(encryptStr));
        System.out.println((new StringBuilder("Decode:")).append(crypt.decrypt(encryptStr)));
    }

    private byte desKey[];
}
