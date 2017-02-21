
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

    public static byte[] crypt (int mode, String key, byte[] inputByteArray) {
        byte[] outputByteArray = new byte[0];

        try {
            MessageDigest md = MessageDigest.getInstance ("MD5");
            byte[] hash = md.digest (key.getBytes());
            Cipher cipherAES = Cipher.getInstance (algorithm);
            Key secretKey = new SecretKeySpec (hash, algorithm);

            switch (mode) {
            case 0:
                cipherAES.init (Cipher.ENCRYPT_MODE, secretKey);
                break;

            case 1:
                cipherAES.init (Cipher.DECRYPT_MODE, secretKey);
                break;

            default:

            }

            outputByteArray = cipherAES.doFinal (inputByteArray);

        } catch (Exception e) {
            switch (mode) {
            case 0:
                System.out.print ("Error encrypting contents to file.\n" + e +
                    "\n");
                break;

            case 1:
                System.out.print ("Error decrypting contents to file.\n" + e +
                    "\n");
                break;

            default:

            }

            System.exit (1);
        }

        return outputByteArray;

    } //end stc byte[] crypt (int, String, byte[])

} //end class TodoUtil
