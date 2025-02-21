package io.github.wishsummer.common.core.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;

public class DesUtils {

    public static byte[] encrypt(byte[] message, String key, String iv,
                                 String model) throws Exception {
        // 指定加密模式 如"DES/CBC/NoPadding"
        Cipher cipher = Cipher.getInstance(model);

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(message);
    }


    public static String decrypt(byte[] message, String key, String iv, String model) throws Exception {
        // 指定加密模式 如"DES/CBC/NoPadding"
        Cipher cipher = Cipher.getInstance(model);

        // 生成key
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // 生成iv
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] decryptMessage = cipher.doFinal();

        return new String(decryptMessage, "UTF-8");
    }


}
