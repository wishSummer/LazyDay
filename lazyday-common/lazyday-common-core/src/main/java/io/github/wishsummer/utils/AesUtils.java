package io.github.wishsummer.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AesUtils {

    public static String Encrypt(byte[] encData, String secretKey, String vector) throws Exception {

        if (secretKey == null) {
            return null;
        }

        // 创建密钥生成对象
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        // 使用 SHA1PRNG 算法创建随机数生成器
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        // 随机数生成器 设置种子
        secureRandom.setSeed(secretKey.getBytes());
        // 基于随机数生成器初始化密钥
        kg.init(256, secureRandom);
        // 未指定算法 仅设置随机数生成种子
        //kg.init(256,new SecureRandom(secretKey.getBytes())); //linux系统用

        // 生成密钥
        SecretKey sk = kg.generateKey();
        byte[] raw = sk.getEncoded();

        SecretKeySpec aes = new SecretKeySpec(secretKey.getBytes(), "AES");

        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(vector.getBytes());

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encrypted = cipher.doFinal(encData);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // AES decryption method
    public static String decrypt(String encryptMessage, String secretKey, String iv) throws Exception {
        // Convert key and IV to byte arrays
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = Base64.getDecoder().decode(iv);

        // Create AES key and IV parameter specification
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        // Initialize Cipher for AES decryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // Decrypt and return result as a string
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptMessage));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
