package id.ahmadnbl.skeletonproject.helper;

/**
 * Created by billy on 10/4/16.
 *
 */

public class SmsUtil {

    public static String getSignupVerificationFormat(int verifyCode) {
        return "This message is for verifying your number. Ref: " + verifyCode;
    }

    public static String getVerifyCodeFromSms(String msg){
        String[] msgs = msg.split(" ");
        // check if length is equal with format
        // if equal, it is maybe valid
        if (msgs.length == 9) {
            return msgs[msgs.length-1];
        }
        return "";
    }


}
