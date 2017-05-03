package id.ahmadnbl.skeletonproject.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by billy on 10/4/16.
 *
 */

public class SmsOTPReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsOTPReceiver";

    private SmsListener listener;

    public SmsOTPReceiver() {
    }

    public SmsOTPReceiver(SmsListener l){
        listener = l;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])                                                                                                    pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    Log.d(TAG, "onReceive: phone=" + phoneNumber);
                    String message = currentMessage.getDisplayMessageBody();
                    listener.smsCallBack(message);
                }
            }
        } catch (Exception e) {}
    }

    public interface SmsListener {
        void smsCallBack(String message);
    }
}
