package org.mac.security;

import java.io.File;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

/**
 * A tester for the CryptoUtils class.
 *
 *
 */
public class CryptoUtilsTest {

    public static void runDec() {
//		String key = "Sat Kartar Gur Bar Akal";
//		File inputFile = new File("document.txt");
//		File encryptedFile = new File("document.encrypted");
//		File decryptedFile = new File("document.decrypted");
        String input = "125hjGLhksRmgdkT";
//		
//		try {
//			CryptoUtils.encrypt(key, input);
//			CryptoUtils.decrypt(key, input);
//		} catch (CryptoException ex) {
//			System.out.println(ex.getMessage());
//			ex.printStackTrace();
//		}
        try {
//            SecureRandom random = new SecureRandom();
//            byte[] salt = new byte[16];
//            random.nextBytes(salt);
////                 String key = "Mary has one cat1";
//            KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 256); // AES-256
//            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//            byte[] key = f.generateSecret(spec).getEncoded();
            
//            File inputFile = new File("D:/macbackup/backup05-05-2017-11-7-29.macbk");
//            File encryptedFile = new File("D:/macbackup/document.encrypted");
//            File decryptedFile = new File("D:/macbackup/document.decrypted");
//
//            byte[] key = ("mac2gkhy").getBytes("UTF-8");
//            MessageDigest sha = MessageDigest.getInstance("SHA-1");
//            key = sha.digest(key);
//            key = Arrays.copyOf(key, 16);
//
//            CryptoUtils.encrypt(key, inputFile, encryptedFile);
//            CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
