package id.ahmadnbl.skeletonproject.data.source.sharedpreferences;

import android.util.Base64;

import id.ahmadnbl.skeletonproject.helper.Crypto;

/**
 * Created by billy on 8/8/16.
 * Application-related configuration keys
 */
public enum AppPrefKey {
    FCM_ID,
    X_APP_TOKEN;

    @Override
    public String toString() {
        String b64S = Crypto.Companion.base64(super.toString(), Base64.NO_WRAP);
        return b64S.replace("=", "<><").replace("m", "<#&>");
    }
}
