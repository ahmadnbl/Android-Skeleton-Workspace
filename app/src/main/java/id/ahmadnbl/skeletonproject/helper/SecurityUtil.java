package id.ahmadnbl.skeletonproject.helper;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by billy on 1/12/16.
 *
 */

public class SecurityUtil {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private final static String LIST_BANK_KEY = "jn3p4y";

    public static String generateSecurityKey(String account, String password) {
        String hash = null;
        try {
            String plainSecurityKey = account + PassDigest.SHA256(password);
            byte[] bytesOfMessage = plainSecurityKey.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytesOfMessage, 0, bytesOfMessage.length);
            bytesOfMessage = md.digest();
            hash = bytesToHex(bytesOfMessage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            return hash != null ? hash.toLowerCase() : "GenerateSecurityKeyError";
        }
    }

    public static String generateListBankSGN() {
        String key = LIST_BANK_KEY;
        String hash = null;
        try {
            byte[] bytesOfMessage = key.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytesOfMessage, 0, bytesOfMessage.length);
            bytesOfMessage = md.digest();
            hash = bytesToHex(bytesOfMessage);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            return hash != null ? hash.toLowerCase() : "generateSecurityError";
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
