package org.mac.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
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

    public static void encrypt(byte[] key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }
     public static void encrypt(byte[] key, String inputFile, String outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, new File(inputFile), new File(outputFile));
    }

    public static void decrypt(byte[] key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }
    
     public static void decrypt(byte[] key, String inputFile, String outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, new File(inputFile), new File(outputFile));
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
