package com.gdev.common.other;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts or decrypts a file.
 *
 * @author www.codejava.net
 *
 */
public class CryptoUtils {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static String key = "abc567";

    private static byte[] getkey() throws Exception{
        byte[] key = ("mac2gkhy").getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        return key;
    }

    public static void encrypt(byte[] key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void encrypt( String inputFile, String outputFile)
            throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, getkey(), new File(inputFile), new File(outputFile));
    }

    public static void decrypt(byte[] key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt( String inputFile, String outputFile)
            throws Exception {
        doCrypto(Cipher.DECRYPT_MODE, getkey(), new File(inputFile), new File(outputFile));
    }

    private static String doCrypto(int cipherMode, byte[] key, File inputFile,
            File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
            return outputBytes.toString();
        } catch (Exception ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}
