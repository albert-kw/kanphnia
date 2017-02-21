
import java.util.Scanner;

import java.io.File;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.security.MessageDigest;
import java.security.Key;


public class TodoUtil {

    private static Scanner input = new Scanner (System.in);

    private static String algorithm = "AES";


    public static String prompt (String message) {
        String userInput = "";

        do {
            System.out.print (message);
            userInput = input.nextLine();

        } while (userInput.isEmpty() ||
            userInput.trim().isEmpty() ||
            userInput == null
        );

        return userInput;
    } //end stc String prompt (String)

    public static byte[] encrypt (String key, byte[] inputByteArray) {
        byte[] byteOutput = new byte[0];

        try {
            MessageDigest md = MessageDigest.getInstance ("MD5");
            byte[] hash = md.digest (key.getBytes());
            Cipher cipherAES = Cipher.getInstance (algorithm);
            Key secretKey = new SecretKeySpec (hash, algorithm);
            cipherAES.init (Cipher.ENCRYPT_MODE, secretKey);

            byteOutput = cipherAES.doFinal (inputByteArray);

        } catch (Exception e) {
            System.out.print ("Error encrypting contents to file.\n" + e);
        }

        return byteOutput;

    } //end stc byte[] encrypt (String, byte[])

    public static byte[] decrypt (String key, byte[] inputByte) {
        byte[] byteOutput = new byte[0];

        try {
            MessageDigest md = MessageDigest.getInstance ("MD5");
            byte[] hash = md.digest (key.getBytes());
            Cipher cipherAES = Cipher.getInstance (algorithm);
            Key secretKey = new SecretKeySpec (hash, algorithm);
            cipherAES.init (Cipher.DECRYPT_MODE, secretKey);

            byteOutput = cipherAES.doFinal (inputByte);

        } catch (Exception e) {
            System.out.print ("Error decrypting contents from file.\n" + e);
        }

        return byteOutput;
    } //end stc byte[] decrypt (String, byte[])

} //end class TodoUtil
