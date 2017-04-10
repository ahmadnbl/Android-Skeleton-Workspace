package id.ahmadnbl.skeletonproject.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by billy on 1/12/16.
 *
 */

public class PassDigest {

    public static String digest(String plain) throws Exception {
        if (plain == null)
            return null;
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(plain.getBytes("UTF-16"));
            byte bs[] = sha.digest();
            StringBuffer res = new StringBuffer();
            for (int ix = 0; ix < bs.length; ix++) {
                int i;
                byte b = bs[ix];
                if (b > 0)
                    i = b;
                else
                    i = 256 + b;
                int d = i / 16;
                if (d > 9)
                    res.append((char) ('A' + d - 10));
                else
                    res.append((char) ('0' + d));
                d = i % 16;
                if (d > 9)
                    res.append((char) ('A' + d - 10));
                else
                    res.append((char) ('0' + d));
            }
            return res.toString();
        } catch (Exception ex) {
            throw new Exception("Digest Exception", ex);
        }
    }


    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA256(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        byte[] digest = md.digest();
        return convertToHex(digest);

        //md.update(text.getBytes("iso-8859-1"), 0, text.length());
        //byte[] digest = md.digest();
        //return convertToHex(digest);
    }

    static String bin2hex(byte[] data) {
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
    }
}